FROM maven:3.6.2-jdk-11-slim

COPY . /home/Puerto-Rico/docker/java
WORKDIR /home/Puerto-Rico/docker/java
RUN mvn package exec:java
