# Pipeline CI/CD - FireWatch03

## Visão Geral

O pipeline CI/CD do FireWatch03 automatiza os processos de build, teste e deploy da aplicação em ambientes de staging e produção. Utilizamos GitHub Actions como plataforma de orquestração, garantindo que cada etapa seja executada de forma consistente e segura.

## Estrutura do Pipeline

O pipeline está dividido em três jobs principais:

1. **Build e Test**: Compila o código e executa testes automatizados
2. **Deploy Staging**: Deploy automatizado no ambiente de staging (quando código é enviado para a branch develop)
3. **Deploy Production**: Deploy automatizado no ambiente de produção (quando código é enviado para a branch main)

## Workflow do GitHub Actions

```yaml
name: FireWatch03 CI/CD Pipeline

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main, develop ]

jobs:
  build:
    name: Build and Test
    runs-on: ubuntu-latest
    # ... passos de build e teste ...

  deploy-staging:
    name: Deploy to Staging
    needs: build
    if: github.ref == 'refs/heads/develop'
    # ... passos de deploy em staging ...

  deploy-production:
    name: Deploy to Production
    needs: build
    if: github.ref == 'refs/heads/main'
    # ... passos de deploy em produção ...
```

## Etapas Detalhadas

### Build e Test

1. **Checkout do código**: Obtém o código mais recente do repositório
2. **Configuração do JDK 21**: Prepara o ambiente Java
3. **Build com Maven**: Compila o código fonte sem executar testes
4. **Execução de Testes**: Executa a suíte de testes com o perfil de teste
5. **Build da Imagem Docker**: Cria a imagem Docker com configurações específicas para o ambiente
6. **Armazenamento da Imagem**: Salva a imagem Docker como artefato para uso nas etapas de deploy

### Deploy em Staging/Produção

1. **Download da Imagem Docker**: Recupera a imagem Docker criada na etapa de build
2. **Carregamento da Imagem**: Prepara a imagem para execução
3. **Configuração do Ambiente**: Cria arquivo .env com as configurações apropriadas
4. **Simulação de Deploy**: Prepara o ambiente para execução da aplicação

## Segurança no Pipeline

Todas as informações sensíveis (credenciais de banco de dados, tokens de API, senhas) são armazenadas como secrets no GitHub e injetadas no pipeline apenas durante a execução. Isso garante que essas informações nunca sejam expostas nos logs ou no código.

## Segurança no Pipeline

Todas as informações sensíveis são armazenadas como secrets no GitHub:

```yaml
# {% raw %}
docker build \
  --build-arg SPRING_PROFILES_ACTIVE=staging \
  --build-arg SPRING_DATASOURCE_URL="${{ secrets.STAGING_DB_URL }}" \
  --build-arg SPRING_DATASOURCE_USERNAME="${{ secrets.STAGING_DB_USER }}" \
  --build-arg SPRING_DATASOURCE_PASSWORD="${{ secrets.STAGING_DB_PASS }}" \
  -t firewatch03:staging .
# {% endraw %}
```

## Fluxo de Trabalho

1. **Desenvolvimento**: Desenvolvedores trabalham em branches de feature ou bugfix
2. **Pull Request**: Ao concluir o trabalho, criam um PR para a branch develop
3. **Revisão e Testes**: O código é revisado e testado automaticamente
4. **Merge para Develop**: Após aprovação, o código é merge para develop, disparando deploy em staging
5. **Validação em Staging**: Testes manuais ou automatizados são executados no ambiente de staging
6. **Promoção para Main**: Quando pronto para produção, develop é merge para main
7. **Deploy em Produção**: O código é automaticamente implantado no ambiente de produção