FROM openjdk:latest
COPY ./target/Labo02CI-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "Labo02CI-1.0-SNAPSHOT-jar-with-dependencies.jar"]