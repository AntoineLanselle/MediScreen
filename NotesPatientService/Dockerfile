# Use a base image with Java
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Expose the port that the application listens on
EXPOSE 8082

# Copy the compiled JAR file and any other required files to the container's working directory
COPY ./target/*.jar /app/notes-patient.jar

# Define the command to start the application inside the container
CMD ["java", "-jar", "notes-patient.jar"]