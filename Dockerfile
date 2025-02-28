# Use an official OpenJDK image with Maven installed
FROM maven:3.9.6-eclipse-temurin-21

# Set working directory in the container
WORKDIR /app

# Copy the project files
COPY . .

# Build the project using Maven
RUN mvn clean package

# Expose the application port
EXPOSE 8080

# Start the Spring Boot app
CMD ["java", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]

