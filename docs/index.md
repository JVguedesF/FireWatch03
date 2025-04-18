# FireWatch03 - Sistema de Monitoramento para Cidades Inteligentes

## Visão Geral

FireWatch03 é uma aplicação de monitoramento para cidades inteligentes focada na detecção e gestão de incêndios em áreas urbanas. O projeto implementa práticas modernas de DevOps, garantindo integração contínua, entrega contínua e monitoramento em tempo real.

## Objetivos do Projeto

- Criar um sistema de monitoramento escalável para cidades inteligentes
- Implementar práticas DevOps modernas
- Automatizar processos de integração e entrega
- Fornecer monitoramento contínuo de métricas da aplicação

## Arquitetura

```
┌─────────┐      ┌─────────────────┐      ┌─────────┐
│ Cliente │─────▶│ API Spring Boot │◀────▶│ Oracle  │
└─────────┘      └─────────────────┘      └─────────┘
                         ▲
                         │
                         ▼
              ┌────────────────────┐
              │ Prometheus/Grafana │
              └────────────────────┘
```

## Tecnologias Utilizadas

- **Backend**: Java Spring Boot 3.3.4
- **JDK**: OpenJDK 21
- **Banco de Dados**: Oracle
- **CI/CD**: GitHub Actions
- **Containerização**: Docker
- **Orquestração**: Docker Compose
- **Monitoramento**: Prometheus e Grafana (com dashboards Spring Boot System Monitor e Endpoint Metrics)

## Pipeline CI/CD

Nosso pipeline CI/CD automatiza:

1. **Build e Teste**: Compilação e execução de testes automatizados
2. **Containerização**: Criação de imagens Docker otimizadas
3. **Deploy para Staging**: Quando código é enviado para a branch develop
4. **Deploy para Produção**: Quando código é enviado para a branch main

## Containerização

Utilizamos Docker com uma abordagem multi-estágio para:

1. **Otimização de Imagem**: Redução do tamanho final da imagem
2. **Segurança Aprimorada**: Execução como usuário não-root
3. **Consistência**: Mesmo ambiente em desenvolvimento, staging e produção

## Monitoramento

A aplicação é monitorada através de:

1. **Prometheus**: Coleta de métricas de performance
2. **Grafana**: Visualização de dados em dashboards personalizados:
   - Spring Boot 2.1 System Monitor
   - Spring Boot Endpoint Metrics
3. **Spring Actuator**: Exposição de métricas da aplicação

## Ambientes

- **Desenvolvimento**: Configuração local para desenvolvimento
- **Staging**: Ambiente de teste antes da produção
- **Produção**: Ambiente para usuários finais

## Requisitos

- Docker e Docker Compose
- JDK 21 (para desenvolvimento local)
- Maven (para desenvolvimento local)

## Execução Local

1. Clone o repositório
2. Configure o arquivo `.env` com as credenciais necessárias
3. Execute o deployment com `./deploy.sh [staging|prod]`
4. Acesse a aplicação em http://localhost:8080

## Arquivos de Configuração

O projeto utiliza arquivos de configuração separados para diferentes ambientes:

- `.env.staging`
- `.env.production`
- Outros ambientes específicos (ex: `.env.homolog`, `.env.test`)

```properties
SPRING_PROFILES_ACTIVE=${AMBIENTE}  # dev, staging, prod

SPRING_DATASOURCE_URL=jdbc:${BANCO_DE_DADOS}://${HOST}:${PORTA}/${SERVICO_OU_BANCO}
SPRING_DATASOURCE_USERNAME=${USUARIO_DB}
SPRING_DATASOURCE_PASSWORD=${SENHA_DB}  # Armazenar em vault seguro

API_SECURITY_TOKEN_SECRET=${CHAVE_SECRETA_JWT}  # Gerar chave forte
GRAFANA_PASSWORD=${SENHA_ADMIN_GRAFANA}  # Senha complexa

DOCKER_IMAGE=${REGISTRY}/${PROJETO}:${VERSAO}
```

## Script de Deployment

O script `deploy.sh` automatiza o processo de implantação para diferentes ambientes:

```bash
#!/bin/bash
set -euo pipefail

if [[ $# -ne 1 ]]; then
  echo "Uso: $0 [staging|prod]"
  exit 1
fi

ENV="$1"
case "$ENV" in
  staging)
    ENV_FILE=".env.staging"
    TAG="firewatch03:staging"
    ;;
  prod|production)
    ENV_FILE=".env.production"
    TAG="firewatch03:production"
    ;;
  *)
    echo "Ambiente inválido: $ENV"
    echo "Use: staging ou prod"
    exit 1
    ;;
esac

echo "→ Iniciando deploy para: $ENV"
echo "   usando arquivo: $ENV_FILE"
cp "$ENV_FILE" .env

echo "→ Build da imagem Docker com tag: $TAG"
docker build -t "$TAG" .

echo "→ Parando containers atuais"
docker-compose down

echo "→ Verificando conexão com o banco de dados..."
if ! nc -zv oracle.fiap.com.br 1521; then
  echo "Erro: Não foi possível conectar ao banco oracle.fiap.com.br:1521"
  exit 1
fi

echo "→ Subindo containers em background"
docker-compose up -d

# Extrai GRAFANA_PASSWORD direto do .env
G_PASS=$(grep '^GRAFANA_PASSWORD=' .env | cut -d= -f2-)

echo
echo "✅ Ambiente $ENV iniciado!"
echo "   • App:       http://localhost:8080"
echo "   • Prometheus: http://localhost:9090"
echo "   • Grafana:   http://localhost:3000 (admin/$G_PASS)"
```

O script realiza as seguintes operações:
1. Verifica se o ambiente especificado é válido (staging ou prod)
2. Copia o arquivo de configuração apropriado
3. Constrói a imagem Docker com a tag correspondente
4. Para os containers em execução
5. Verifica a conexão com o banco de dados Oracle
6. Inicia os novos containers em background
7. Exibe informações de acesso para a aplicação e ferramentas de monitoramento
