FROM maven:3.6.3-jdk-11 AS build
WORKDIR /app

COPY pom.xml .
COPY src src

RUN mvn -U clean package

FROM openjdk:11-jdk

COPY --from=build /app/target/ai-hub-server-1.0.0-SNAPSHOT-exec.jar /app.jar

EXPOSE 9999

ENTRYPOINT ["java", "-jar", "/app.jar"]