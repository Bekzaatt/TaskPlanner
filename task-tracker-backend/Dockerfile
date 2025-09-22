FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY .. .
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:21-jdk-slim
COPY --from=build /app/task-tracker-backend/target/task-tracker-backend-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
