FROM openjdk:11-jdk-slim
COPY . /home/Puerto-Rico/docker/java
WORKDIR /home/Puerto-Rico/docker/java
ARG JAR_FILE=/target/Serveur-0.1-SNAPSHOT.jar
COPY ${JAR_FILE} serveur.jar
ENTRYPOINT java -jar serveur.jar
