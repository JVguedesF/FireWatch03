# Containerização e Orquestração - FireWatch03

## Visão Geral

O FireWatch03 utiliza Docker para containerização e Docker Compose para orquestração de serviços, garantindo consistência entre ambientes de desenvolvimento, staging e produção. Esta abordagem facilita a implantação e o escalonamento da aplicação.

## Estrutura de Containerização

### Dockerfile Multi-estágio

Nosso Dockerfile implementa uma abordagem de compilação em múltiplos estágios:

1. **Estágio de Build**: Usa a imagem `maven:3.9.9-eclipse-temurin-21` para compilar o código fonte
2. **Estágio de Runtime**: Usa a imagem `openjdk:21-slim` para executar a aplicação, mantendo a imagem final o mais leve possível

```Dockerfile
# Fase de construção usando Maven e OpenJDK 21
FROM maven:3.9.9-eclipse-temurin-21 AS build
COPY src /app/src
COPY pom.xml /app
WORKDIR /app
RUN mvn clean install

# Fase de execução otimizada
FROM openjdk:21-slim

# Cria usuário e grupo não-root
RUN addgroup --system --gid 1001 appuser && \
    adduser --system --uid 1001 --gid 1001 appuser

# Copia o JAR da fase de construção
COPY --from=build /app/target/FireWatch03-0.0.1-SNAPSHOT.jar /app/FireWatch03-0.0.1-SNAPSHOT.jar

# Configura permissões e ambiente
WORKDIR /app
RUN chown -R appuser:appuser /app

USER appuser

EXPOSE 8080
CMD ["java", "-jar", "FireWatch03-0.0.1-SNAPSHOT.jar"]
```

### Benefícios da Abordagem Multi-estágio

1. **Tamanho reduzido**: A imagem final contém apenas o necessário para execução
2. **Segurança aprimorada**: Menos dependências significa menor superfície de ataque
3. **Isolamento**: As ferramentas de build não estão presentes no ambiente de runtime

## Melhores Práticas Implementadas

### Segurança

- **Usuário não-root**: A aplicação é executada como um usuário não-privilegiado (appuser)
- **Permissões mínimas**: O contêiner tem acesso apenas aos recursos necessários

### Otimização

- **Imagem base leve**: Utilizamos `openjdk:21-slim` para minimizar o tamanho da imagem
- **Cacheable layers**: Estruturamos o Dockerfile para maximizar o uso de cache durante builds

### Configuração

- **Externalização de configurações**: Todas as configurações específicas do ambiente são injetadas via variáveis de ambiente
- **Arquivos .env**: Utilizamos arquivos .env específicos para cada ambiente (staging, produção)

## Orquestração com Docker Compose

O Docker Compose é utilizado para orquestrar os seguintes serviços:

1. **app**: A aplicação FireWatch03
2. **db**: Banco de dados Oracle XE
3. **prometheus**: Serviço de coleta de métricas
4. **grafana**: Plataforma de visualização de métricas

```yaml
version: '3.8'

services:
  app:
    image: ${DOCKER_IMAGE:-firewatch03:latest}
    # ...configurações

  db:
    image: gvenzl/oracle-xe:21-slim
    # ...configurações

  prometheus:
    image: prom/prometheus
    # ...configurações

  grafana:
    image: grafana/grafana
    # ...configurações
```

### Redes e Volumes

- **Rede dedicada**: Os serviços se comunicam através de uma rede dedicada `firewatch-network`
- **Volumes persistentes**: Dados do Oracle e do Grafana são armazenados em volumes persistentes

## Estratégia de Deployment

### Script de Deployment Automatizado

Utilizamos um script `deploy.sh` para automatizar o deployment em diferentes ambientes:

```bash
./deploy.sh staging  # Deploy no ambiente de staging
./deploy.sh prod     # Deploy no ambiente de produção
```

O script realiza as seguintes operações:
1. Seleciona o arquivo de configuração apropriado (.env.staging ou .env.production)
2. Constrói a imagem Docker com a tag correta
3. Para os containers existentes
4. Inicia os novos containers com as configurações atualizadas

### Processo de Rollback

Em caso de falha no deployment, podemos facilmente realizar um rollback:

1. Identificar a versão estável anterior
2. Especificar a tag correspondente na variável `DOCKER_IMAGE` no arquivo .env
3. Executar `docker-compose up -d` para implantar a versão anterior

## Monitoramento dos Containers

### Prometheus

O Prometheus coleta métricas da aplicação através do endpoint `/actuator/prometheus`, configurado em `prometheus.yml`:

```yaml
scrape_configs:
  - job_name: 'firewatch03'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['app:8080']
```

### Grafana

O Grafana visualiza as métricas coletadas pelo Prometheus, apresentando dashboards para:
- Uso de CPU e memória
- Requisições por segundo
- Tempo de resposta
- Métricas específicas da aplicação

## Conclusão

A estratégia de containerização e orquestração do FireWatch03 permite:

1. **Consistência entre ambientes**: O mesmo container é executado em desenvolvimento, staging e produção
2. **Facilidade de deployment**: O processo de implantação é automatizado e consistente
3. **Observabilidade**: Prometheus e Grafana fornecem visibilidade do comportamento da aplicação
4. **Escalabilidade**: A arquitetura baseada em containers facilita o escalonamento horizontal

Esta abordagem baseada em DevOps fortalece o ciclo de vida da aplicação, desde o desenvolvimento até a produção, garantindo que as mudanças sejam testadas e implantadas com segurança e eficiência.