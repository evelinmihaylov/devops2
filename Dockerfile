# Use OpenJDK 17 as base image
FROM openjdk:17

# Set working directory inside the container
WORKDIR /app

# Copy the packaged JAR file from Maven build
COPY ./target/Labo02CI-0.1.0.2-jar-with-dependencies.jar /app/app.jar

# Run the application
ENTRYPOINT ["java", "-cp", "app.jar", "com.napier.sem.App"]