# FROM eclipse-temurin:24-jdk-jammy AS build
# VOLUME /tmp
# ARG JAR_FILE=target/*.jar
# COPY ${JAR_FILE} app.jar
# # Ensure the 'data' directory exists and copy the H2 database file
# RUN mkdir -p /data
# COPY data/factoryDB.mv.db /data/factoryDB.mv.db
# # Assuming the actual H2 file is factoryDB.mv.db
# #COPY data/factoryDB.trace.db /data/factoryDB.trace.db 
# # If trace file exists
# ENTRYPOINT ["java","-jar","/app.jar"]

# Stage 1: Build the Spring Boot application
FROM eclipse-temurin:24-jdk-alpine AS builder

WORKDIR /app

# Copy the Maven/Gradle project files
COPY pom.xml .
COPY src ./src

# Build the Spring Boot application (using Maven as an example)
# RUN mvn clean package -DskipTests

# Stage 2: Create the final image with a minimal JRE
FROM eclipse-temurin:24-jre-alpine

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your Spring Boot application runs on
EXPOSE 8080

# Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]