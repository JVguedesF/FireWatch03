# Evidências de Execução - FireWatch03

## 1. Pipeline CI/CD

### 1.1.1 Etapa de Build staging

![Etapa de Build and Test com staging](../images/pipeline/builstaging.png)

*Descrição: Etapa de build and test com staging sendo executada com sucesso.*

### 1.1.2 Etapa de Build production

![Etapa de Build and Test com Production](../images/pipeline/buildprod.png)

*Descrição: Etapa de build and test com production sendo executada com sucesso.*

### 1.3. Etapa de build and test

![Etapa de Teste](../images/pipeline/buildtest.png)

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

### 2.2. Containers Staging em Execução

![Docker Containers](../images/containers/docker-ps.png)

*Descrição: Containers Docker em execução, incluindo a aplicação, Prometheus e Grafana.*

### 2.3.1 Build imagem Docker Staging

![Container Logs](../images/containers/buildimagemstaging.png)

*Descrição: Build da imagem de Staging bem sucedida.*

### 2.3.2 Build imagem Docker Production

![Container Logs](../images/containers/buildimagemproduction.png)

*Descrição: Build da imagem de Production bem sucedida.*

## 3. Monitoramento

### 3.1. Prometheus

![Prometheus Targets](../images/monitoring/prometheus-targets.png)

*Descrição: Prometheus mostrando o FireWatch03 como alvo de monitoramento.*

![Prometheus Metrics_1](../images/monitoring/prometheus-metrics1.png)
![Prometheus Metrics_2](../images/monitoring/prometheus-metrics2.png)
![Prometheus Metrics33](../images/monitoring/prometheus-metrics3.png)

*Descrição: Métricas sendo coletadas pelo Prometheus.*

### 3.2. Grafana

![Grafana Dashboard](../images/monitoring/grafana-dashboard.png)
![Performance Metrics](../images/monitoring/grafana-performance.png)

*Descrição: Dashboard do Grafana mostrando métricas em tempo real.*