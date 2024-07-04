FROM eclipse-temurin:19-alpine

# Create and set the working directory
WORKDIR /detyrekursi

# Volume for temporary files
VOLUME /tmp

# Expose the application port
EXPOSE 8080

# Build argument for the JAR file location
ARG JAR_FILE=target/SpringBootRegistrationLogin-1.0.jar

# Copy the JAR file into the container
COPY ${JAR_FILE} SpringBootRegistrationLogin.jar

# Entry point to run the application
ENTRYPOINT ["java", "-jar", "SpringBootRegistrationLogin.jar"]
