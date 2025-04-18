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