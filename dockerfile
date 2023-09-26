FROM openjdk:17-jdk-slim-buster

COPY service.jar /opt/app.jar

EXPOSE 8080
ENTRYPOINT java -jar /opt/app.jar