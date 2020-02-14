<<<<<<< HEAD
FROM openjdk:11-jdk-slim

=======
FROM maven:3.6.2-jdk-11-slim
>>>>>>> 7f3ea4bcd937c2850f91d0acc6685b48fab9d5ad
COPY . /home/Puerto-Rico/docker/java
WORKDIR /home/Puerto-Rico/docker/java
<<<<<<< HEAD
RUN mvn package exec:java
CMD ["start"]


=======
ARG JAR_FILE=/target/PuertoRico-0.1-SNAPSHOT-jar-with-dependencies.jar
COPY ${JAR_FILE} jeux.jar
ENTRYPOINT java -jar jeux.jar
>>>>>>> docker
