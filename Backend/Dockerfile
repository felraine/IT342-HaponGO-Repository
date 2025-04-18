# Use an OpenJDK base image (this version depends on your Spring Boot version)
FROM openjdk:17-jdk-slim

# Expose the port the application will run on (Spring Boot defaults to 8080)
EXPOSE 8080

# Copy the Spring Boot .jar file from the target directory to the container
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Command to run the application (this will be executed when the container starts)
ENTRYPOINT ["java", "-jar", "/app.jar"]
