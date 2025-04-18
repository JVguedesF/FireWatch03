# FireWatch03 - Sistema de Monitoramento para Cidades Inteligentes

## Sobre o Projeto

FireWatch03 é uma aplicação de monitoramento para cidades inteligentes, desenvolvida com Java Spring Boot. O sistema oferece uma API REST para monitoramento de dados urbanos, com foco na detecção e gerenciamento de incêndios em áreas urbanas, implementando práticas modernas de DevOps para garantir integração contínua, entrega contínua e monitoramento em tempo real.

## Arquitetura

O projeto utiliza:
- **Backend**: Java Spring Boot 3.3.4
- **JDK**: OpenJDK 21
- **Banco de Dados**: Oracle XE
- **CI/CD**: GitHub Actions
- **Containerização**: Docker
- **Orquestração**: Docker Compose
- **Monitoramento**: Prometheus e Grafana

## Requisitos

Para executar o projeto, você precisará:
- Docker
-  Docker Compose
- Git

## Executando o Projeto

### 1. Clone o Repositório

```bash
git clone https://github.com/seu-usuario/FireWatch03.git
cd FireWatch03
```

### 2. Configure os Arquivos de Ambiente

O projeto utiliza arquivos `.env` para configurações específicas de cada ambiente. Dois arquivos são fornecidos:

- `.env.staging`: Configurações para ambiente de testes
- `.env.production`: Configurações para ambiente de produção

Edite estes arquivos conforme necessário, inserindo suas credenciais de banco de dados e outras configurações.

### 3. Configurando Permissões

Torne o script de deploy executável:

```bash
chmod +x deploy.sh
```

### 4. Executando o Deploy

Para iniciar o ambiente de staging:

```bash
./deploy.sh staging
```

Para iniciar o ambiente de produção:

```bash
./deploy.sh prod
```

O script de deploy realizará as seguintes operações:
1. Seleção do arquivo de ambiente correto (staging ou production)
2. Build da imagem Docker com a tag apropriada
3. Parada de containers existentes
4. Verificação de conexão com o banco de dados
5. Inicialização dos novos containers

### 5. Acessando a Aplicação

Após a execução bem-sucedida do deploy, você poderá acessar:

- **API**: http://localhost:8080
- **Prometheus**: http://localhost:9090
- **Grafana**: http://localhost:3000 (credenciais serão mostradas no terminal após execução)

## Pipeline CI/CD

O projeto implementa um pipeline CI/CD completo com GitHub Actions, que é acionado automaticamente em:
- Pushes para as branches `main` e `develop`
- Pull requests para as branches `main` e `develop`

O pipeline realiza:
1. **Build e testes**: Compila o código e executa os testes automatizados
2. **Containerização**: Cria imagens Docker otimizadas
3. **Deploy para staging**: Quando código é enviado para a branch `develop`
4. **Deploy para produção**: Quando código é enviado para a branch `main`

## Containerização

O projeto utiliza Docker com uma abordagem multi-estágio para:
- Reduzir o tamanho da imagem final
- Melhorar a segurança (execução como usuário não-root)
- Garantir consistência entre ambientes

## Monitoramento

A aplicação é monitorada através de:
- Prometheus: coleta de métricas de performance via Spring Actuator
- Grafana: visualização em dashboards personalizados:
  - Spring Boot 2.1 System Monitor
  - Spring Boot Endpoint Metrics

## Estrutura do Projeto

```
FireWatch03/
├── .github/workflows/    # Configuração do pipeline CI/CD
├── docs/                 # Documentação detalhada
├── monitoring/           # Configurações de Prometheus e Grafana
├── src/                  # Código-fonte da aplicação
├── .env.production       # Variáveis de ambiente para produção
├── .env.staging          # Variáveis de ambiente para staging
├── docker-compose.yml    # Configuração de serviços
├── Dockerfile            # Instruções para build da imagem
├── deploy.sh             # Script de deployment
└── pom.xml               # Dependências Maven
```

## Segurança

O projeto implementa várias práticas de segurança:
- Execução de containers como usuário não-privilegiado
- Gestão de secrets via variáveis de ambiente
- Imagem Docker base mínima para reduzir superfície de ataque

## Solução de Problemas

Se encontrar problemas ao executar o projeto:

1. **Erro de conexão com banco de dados**: Verifique se o host do banco de dados está acessível e se as credenciais estão corretas no arquivo `.env`

2. **Problemas com permissão**: Certifique-se de que o script `deploy.sh` tem permissões de execução

3. **Erros no Docker**: Verifique se o Docker e Docker Compose estão instalados e funcionando corretamente

Para mais detalhes sobre cada componente, consulte a documentação em `/docs`.
