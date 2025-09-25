# Use an official Java runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app
# Copy the project JAR file into the container
COPY target/factory-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot application listens on (optional, but good practice)
EXPOSE 8080

# Run the JAR file

ENTRYPOINT ["java", "-jar", "app.jar"]