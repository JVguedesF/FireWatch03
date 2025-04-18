# Evidências de Execução - FireWatch03

## 1. Pipeline CI/CD

### 1.1. Visão Geral do Pipeline

![Visão Geral do Pipeline](../images/pipeline/overview.png)

*Descrição: Captura de tela mostrando o pipeline completo executado no GitHub Actions.*

### 1.2. Etapa de Build

![Etapa de Build](../images/pipeline/build.png)

*Descrição: Etapa de build sendo executada com sucesso.*

### 1.3. Etapa de Teste

![Etapa de Teste](../images/pipeline/test.png)

*Descrição: Testes sendo executados com sucesso.*

### 1.4. Deploy em Staging

![Deploy Staging](../images/pipeline/deploy-staging.png)

*Descrição: Implantação bem-sucedida no ambiente de staging.*

### 1.5. Deploy em Produção

![Deploy Production](../images/pipeline/deploy-production.png)

*Descrição: Implantação bem-sucedida no ambiente de produção.*

## 2. Containerização

### 2.1. Imagens Docker

![Docker Images](../images/containers/docker-images.png)

*Descrição: Imagens Docker criadas para ambientes de staging e produção.*

### 2.2. Containers em Execução

![Docker Containers](../images/containers/docker-ps.png)

*Descrição: Containers Docker em execução, incluindo a aplicação, Prometheus e Grafana.*

### 2.3. Logs de Execução

![Container Logs](../images/containers/container-logs.png)

*Descrição: Logs mostrando a inicialização bem-sucedida dos containers.*

## 3. Execução da Aplicação

### 3.1. Ambiente de Staging

![Staging Environment](../images/application/staging-app.png)

*Descrição: Aplicação rodando no ambiente de staging.*

### 3.2. Ambiente de Produção

![Production Environment](../images/application/production-app.png)

*Descrição: Aplicação rodando no ambiente de produção.*

### 3.3. Endpoints Actuator

![Actuator Endpoints](../images/application/actuator.png)

*Descrição: Endpoints Spring Boot Actuator expostos para monitoramento.*

## 4. Monitoramento

### 4.1. Prometheus

![Prometheus Targets](../images/monitoring/prometheus-targets.png)

*Descrição: Prometheus mostrando o FireWatch03 como alvo de monitoramento.*

![Prometheus Metrics](../images/monitoring/prometheus-metrics.png)

*Descrição: Métricas sendo coletadas pelo Prometheus.*

### 4.2. Grafana

![Grafana Dashboard](../images/monitoring/grafana-dashboard.png)

*Descrição: Dashboard do Grafana mostrando métricas em tempo real.*

![Performance Metrics](../images/monitoring/grafana-performance.png)

*Descrição: Métricas de performance da aplicação no Grafana.*
