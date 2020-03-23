# Dockerfile
FROM openjdk:11-jre

RUN mkdir app

ADD cadastro-1.0-SNAPSHOT.jar app/cardapio.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=docker", "app/cardapio.jar"]