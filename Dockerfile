FROM maven:3.9.4-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml /app/
COPY src /app/src/
COPY config /app/config/

RUN mvn clean install -DskipTests

FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

RUN mkdir /app/files

COPY --from=builder /app/target/repo-v1.jar /app/repo-v1.jar
COPY --from=builder /app/config/ /app/config/

EXPOSE 8900

ENTRYPOINT ["java", "-jar", "/app/repo-v1.jar"]
