# Etapa 1 — Build com Maven e Amazon Corretto 21
FROM maven:3.9.9-amazoncorretto-21 AS builder
WORKDIR /project-music
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2 — Runtime
FROM amazoncorretto:21-alpine-jdk
WORKDIR /project-music
COPY --from=builder /project-music/target/*.jar music.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "music.jar"]