# build
FROM maven:3.9.9-eclipse-temurin-21 AS build
COPY src /app/src
COPY pom.xml /app
WORKDIR /app
RUN mvn clean install

# runtime
FROM openjdk:21-slim
RUN addgroup --system --gid 1001 appuser \
 && adduser --system --uid 1001 --gid 1001 appuser
COPY --from=build /app/target/*.jar /app/app.jar
WORKDIR /app
RUN chown -R appuser:appuser /app
USER appuser
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
