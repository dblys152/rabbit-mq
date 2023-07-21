FROM --platform=linux/amd64 openjdk:17-jdk-alpine3.14

ARG FROM_JAR=build/libs/adapter.jar

COPY ${FROM_JAR} app.jar

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "app.jar"]