FROM openjdk:8-jdk-slim
COPY . /home/Puerto-Rico/server/java
WORKDIR /home/Puerto-Rico/server/java
ARG JAR_FILE=/target/Serveur-1.1.0-SNAPSHOT.jar
COPY ${JAR_FILE} serveur.jar
ENTRYPOINT java -jar serveur.jar
