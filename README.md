# FireWatch03 - Sistema de Monitoramento para Cidades Inteligentes

## Sobre o Projeto

FireWatch03 é uma aplicação de monitoramento para cidades inteligentes, desenvolvida como parte do desafio de DevOps. O sistema utiliza Java Spring Boot para o backend e oferece uma API REST para monitoramento de dados urbanos, com foco em detecção e gerenciamento de incêndios em áreas urbanas.

## Arquitetura

O projeto utiliza:
- **Backend**: Java Spring Boot
- **Banco de Dados**: Oracle XE
- **Monitoramento**: Prometheus e Grafana
- **CI/CD**: GitHub Actions
- **Containerização**: Docker
- **Orquestração**: Docker Compose

## Pipeline CI/CD

Nosso pipeline CI/CD está configurado com GitHub Actions e inclui:

1. **Build e Test**: Compilação e execução de testes automatizados
2. **Containerização**: Criação de imagens Docker
3. **Deploy para Staging**: Quando código é push para a branch develop
4. **Deploy para Produção**: Quando código é push para a branch main

## Requisitos

- Docker
- Docker Compose
- JDK 21 (para desenvolvimento local)
- Maven (para desenvolvimento local)

## Configuração

### Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:

```
# Perfil
SPRING_PROFILES_ACTIVE=dev

# Banco de dados Oracle
SPRING_DATASOURCE_URL=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password

# JWT e Grafana
API_SECURITY_TOKEN_SECRET=your_secret_token
GRAFANA_PASSWORD=your_grafana_password
```

## Execução Local

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/FireWatch03.git
cd FireWatch03
```

2. Configure o arquivo `.env` conforme mostrado acima

3. Execute com Docker Compose:
```bash
docker-compose up -d
```

4. Acesse:
   - API: http://localhost:8080
   - Grafana: http://localhost:3000 (usuário: admin, senha: a definida no .env)
   - Prometheus: http://localhost:9090

## Ambientes

- **Desenvolvimento**: Local, usando as configurações em .env
- **Staging**: Deploy automático quando código é enviado para branch develop
- **Produção**: Deploy automático quando código é enviado para branch main

## Monitoramento

O projeto inclui Prometheus para coleta de métricas e Grafana para visualização. O dashboard padrão do Grafana está configurado para mostrar:

- Uso de CPU e memória
- Requests por segundo
- Tempo de resposta
- Métricas específicas da aplicação
