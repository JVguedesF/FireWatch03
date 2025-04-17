#!/bin/bash
echo "Iniciando ambiente de STAGING..."

# Usar o arquivo .env.staging
cp .env.staging .env

# Construir imagem com tag específica
docker build -t firewatch03:staging .

# Iniciar os containers
docker-compose down
docker-compose up -d

echo "Ambiente de STAGING iniciado!"
echo "Acesse:"
echo "- Aplicação: http://localhost:8080"
echo "- Prometheus: http://localhost:9090"
echo "- Grafana: http://localhost:3000 (admin/GrafanaStaging@2023)"