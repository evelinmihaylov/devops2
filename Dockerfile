FROM openjdk:latest
COPY ./target/Labo02CI-0.1.0.2-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "Labo02CI-0.1.0.2-jar-with-dependencies.jar"]