# Estágio 1: Build
FROM maven:3.9.9-eclipse-temurin-24 AS build
LABEL authors="nobru"
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Estágio 2: Execução
FROM eclipse-temurin:24-jre
WORKDIR /app

# Copia o JAR do estágio de build de forma dinâmica
COPY --from=build /app/target/*.jar app.jar

# Variáveis de ambiente sem valores fixos (serão injetadas pelo Compose)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/irmandade_bot?createDatabaseIfNotExist=true
ENV SPRING_DATASOURCE_USERNAME=${MYSQL_USER}
ENV SPRING_DATASOURCE_PASSWORD=${MYSQL_PASSWORD}
ENV OPENAI_API_KEY=${OPENAI_API_KEY}

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]