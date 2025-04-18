# Evidências de Execução - FireWatch03

## 1. Pipeline CI/CD

### 1.1.1 Etapa de Build Staging
![Etapa de Build and Test com staging](./images/pipeline/builstaging.png)  
*Descrição: Etapa de build and test com staging sendo executada com sucesso.*

### 1.1.2 Etapa de Build Production
![Etapa de Build and Test com Production](./images/pipeline/buildprod.png)  
*Descrição: Etapa de build and test com production sendo executada com sucesso.*

### 1.2. Etapa de Build and Test
![Etapa de Teste](./images/pipeline/buildtest.png)  
*Descrição: Testes sendo executados com sucesso.*

### 1.3. Deploy em Staging
![Deploy Staging](./images/pipeline/deploy-staging.png)  
*Descrição: Implantação bem-sucedida no ambiente de staging.*

### 1.4. Deploy em Produção
![Deploy Production](./images/pipeline/deploy-production.png)  
*Descrição: Implantação bem-sucedida no ambiente de produção.*

---

## 2. Containerização

### 2.1. Imagens Docker
![Docker Images](./images/containers/docker-images.png)  
*Descrição: Imagens Docker criadas para ambientes de staging e production. Nota: Verificar possível typo no comando `grep firewatch83` (deve ser `firewatch03`).*

### 2.2. Containers em Execução
![Docker Containers](./images/containers/docker-ps.png)  
*Descrição: Containers Docker em execução (app, Prometheus e Grafana). Observar formatação da tabela na imagem.*

### 2.3.1 Build da Imagem Docker Staging
![Build Imagem Staging](./images/containers/buildimagemstaging.png)  
*Descrição: Log de build da imagem de staging com aviso sobre `version` obsoleto no compose.*

### 2.3.2 Build da Imagem Docker Production
![Build Imagem Production](./images/containers/buildimagemproduction.png)  
*Descrição: Log de build da imagem de production com aviso semelhante ao staging.*

---

## 3. Monitoramento

### 3.1. Prometheus
#### 3.1.1 Targets
![Prometheus Targets](./images/monitoring/prometheus-targets.png)  
*Descrição: FireWatch03 listado como alvo no Prometheus.*

#### 3.1.2 Métricas
![Métricas 1](./images/monitoring/prometheus-metrics1.png)  
![Métricas 2](./images/monitoring/prometheus-metrics2.png)  
![Métricas 3](./images/monitoring/prometheus-metrics3.png)  
*Descrição: Dados coletados pelo Prometheus em três partes.*

### 3.2. Grafana
#### 3.2.1 Dashboard de Sistema
![Dashboard Grafana](./images/monitoring/grafana-dashboard.png)  
*Descrição: Dashboard "Spring Boot 2.1 System Monitor".*

#### 3.2.2 Métricas de Performance
![Performance Metrics](./images/monitoring/grafana-performance.png)  
*Descrição: Dashboard "Spring Boot Endpoint Metrics".*