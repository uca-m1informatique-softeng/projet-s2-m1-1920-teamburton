FROM openjdk:11-jdk

COPY . /home/Puerto-Rico/docker/java
RUN javac /home/Puerto-Rico/docker/java/src/main/java/puertoricotr/Jeux.java

CMD ["java", "Jeux"]
