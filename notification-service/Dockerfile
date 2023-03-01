FROM maven:3.8.6-eclipse-temurin-17-alpine AS maven

WORKDIR /usr/src/app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ src/
RUN mvn package -DskipTests

FROM openjdk:17
ARG JAR_FILE=notification-service-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app/

COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "notification-service-0.0.1-SNAPSHOT.jar"]
