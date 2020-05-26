FROM openjdk:8-jdk-slim
COPY . /home/Puerto-Rico/docker/java
WORKDIR /home/Puerto-Rico/docker/java
ARG JAR_FILE=./target/Moteur-0.1-SNAPSHOT.jar
COPY ${JAR_FILE} moteur.jar
ENTRYPOINT java -jar moteur.jar
