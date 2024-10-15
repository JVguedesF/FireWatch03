# Usando a imagem oficial do Maven com OpenJDK 21
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Copiando o código-fonte e o arquivo pom.xml para o diretório /app
COPY src /app/src
COPY pom.xml /app

# Definindo o diretório de trabalho
WORKDIR /app

# Executando o Maven para construir o projeto
RUN mvn clean install

# Usando uma imagem leve do OpenJDK 21
FROM openjdk:21-slim

# Copiando o JAR gerado da etapa de build para a imagem final
COPY --from=build /app/target/FireWatch03-0.0.1-SNAPSHOT.jar /app/FireWatch03-0.0.1-SNAPSHOT.jar

# Definindo o diretório de trabalho
WORKDIR /app/

# Expondo a porta 8080
EXPOSE 8080

# Comando para executar o aplicativo
CMD ["java", "-jar", "/app/FireWatch03-0.0.1-SNAPSHOT.jar"]
