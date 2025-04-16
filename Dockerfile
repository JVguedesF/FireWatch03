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