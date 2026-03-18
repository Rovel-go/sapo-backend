FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/*.jar app.jar

# Fly.io solo enruta tráfico al puerto 8080
EXPOSE 8080

# Activo el profile "prod" para que use server.port=8080
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]

