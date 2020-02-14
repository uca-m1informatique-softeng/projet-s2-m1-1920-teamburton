FROM openjdk:11-jdk-slim

COPY . /home/Puerto-Rico/docker/java
WORKDIR /home/Puerto-Rico/docker/java
<<<<<<< HEAD
RUN mvn package exec:java


=======
ARG JAR_FILE=/target/PuertoRico-0.1-SNAPSHOT-jar-with-dependencies.jar
COPY ${JAR_FILE} jeux.jar
ENTRYPOINT java -jar jeux.jar
>>>>>>> docker
