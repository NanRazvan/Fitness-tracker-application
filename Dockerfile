#FROM ubuntu:latest
#LABEL authors="nanc2"
# Use the official OpenJDK 17 image
FROM openjdk:17-jdk-alpine

# Set the location of the app in the container
VOLUME /tmp

# Copy the built jar file into the container
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app.jar"]
