# Containerização e Orquestração - FireWatch03

## Visão Geral

O FireWatch03 utiliza Docker para containerização e Docker Compose para orquestração, garantindo consistência entre os ambientes de desenvolvimento, staging e produção. Esta abordagem facilita o deployment e o escalonamento da aplicação.

## Dockerfile Multi-estágio

Implementamos um Dockerfile com abordagem multi-estágio para otimizar o tamanho e a segurança da imagem final:

```Dockerfile
# Estágio de build
FROM maven:3.9.9-eclipse-temurin-21 AS build
COPY src /app/src
COPY pom.xml /app
WORKDIR /app
RUN mvn clean install

# Estágio de runtime
FROM openjdk:21-slim

# Argumentos e variáveis de ambiente
ARG SPRING_PROFILES_ACTIVE
ARG SPRING_DATASOURCE_URL
# Outras variáveis...

# Configuração de usuário não-root
RUN addgroup --system --gid 1001 appuser && \
    adduser --system --uid 1001 --gid 1001 appuser

# Cópia do JAR e configuração de permissões
COPY --from=build /app/target/FireWatch03-0.0.1-SNAPSHOT.jar /app/FireWatch03-0.0.1-SNAPSHOT.jar
WORKDIR /app
RUN chown -R appuser:appuser /app

USER appuser

EXPOSE 8080
CMD ["java", "-jar", "FireWatch03-0.0.1-SNAPSHOT.jar"]
```

## Orquestração com Docker Compose

#### O Docker Compose orquestra os seguintes serviços:

  1. **app**: Aplicação Java Spring Boot
  2. **prometheus**: Coleta de métricas
  3. **grafana**: Visualização de métricas

```yaml
version: '3.8'

services:
  app:
    image: ${DOCKER_IMAGE}
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE
      # Outras variáveis...
    networks:
      - firewatch-network

  prometheus:
    image: prom/prometheus
    volumes:
      - ./monitoring/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml
    ports:
      - "9090:9090"
    networks:
      - firewatch-network

  grafana:
    image: grafana/grafana
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=${GRAFANA_PASSWORD}
    volumes:
      - grafana-data:/var/lib/grafana
      - ./monitoring/grafana/11378_rev2.json:/etc/grafana/provisioning/dashboards/spring-boot-monitor.json
      - ./monitoring/grafana/17024_rev1.json:/etc/grafana/provisioning/dashboards/spring-boot-endpoints.json
      - ./monitoring/grafana/dashboards.yml:/etc/grafana/provisioning/dashboards/dashboards.yml
    ports:
      - "3000:3000"
    networks:
      - firewatch-network
```

## Boas Práticas Implementadas

### Segurança

- **Execução não-root**: A aplicação executa como usuário não-privilegiado
- **Secrets**: Informações sensíveis são passadas via variáveis de ambiente
- **Imagem base mínima**: Uso de imagem slim para reduzir superfície de ataque

### Configuração

- **Ambiente via variáveis**: Todas as configurações são passadas via variáveis de ambiente
- **Perfis por ambiente**: Uso de perfis Spring (dev, staging, prod) para configurações específicas
- **Arquivo .env**: Configurações centralizadas em arquivos .env por ambiente

## Monitoramento

### Prometheus

Configurado para coletar métricas da aplicação:

```yaml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'firewatch03'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['app:8080']
```

### Grafana

Dashboards personalizados para visualizar:

- Spring Boot 2.1 System Monitor: Foco em métricas do sistema como CPU, memória e JVM
- Spring Boot Endpoint Metrics: Monitoramento de endpoints específicos da aplicação, incluindo taxa de requisições, tempo de resposta e erros

## Deployment Automatizado

O script `deploy.sh` automatiza o processo de deployment:

```bash
./deploy.sh staging  # Deploy no ambiente de staging
./deploy.sh prod     # Deploy no ambiente de produção
```

#### Este script:

1. Seleciona o arquivo `.env` correto  
2. Constrói a imagem Docker com a tag apropriada  
3. Para os containers existentes  
4. Inicia os novos containers com as configurações atualizadas
