#!/bin/bash
echo "Iniciando ambiente de PRODUÇÃO..."

# Usar o arquivo .env.production
cp .env.production .env

# Construir imagem com tag específica
docker build -t firewatch03:production .

# Iniciar os containers
docker-compose down
docker-compose up -d

echo "Ambiente de PRODUÇÃO iniciado!"
echo "Acesse:"
echo "- Aplicação: http://localhost:8080"
echo "- Prometheus: http://localhost:9090"
echo "- Grafana: http://localhost:3000 (admin/GrafanaProd@2023!Secure)"