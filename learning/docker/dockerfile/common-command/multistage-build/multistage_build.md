# Multistage Builds

- **purpose:**
  - allows building an application in one stage and copying only the required files into a smaller, final image;
  - reduces image size by avoiding unnecessary dependencies in the final container;
  - improves security and performance by keeping the final image clean and optimized;
  - see more details within the [multi-stage builds chapter](../../index.md).

- **syntax:**

    ```dockerfile
    FROM <image> AS <name>   # define a build stage with a name
    # build commands here
    
    FROM <final-image>       # start the final image
    COPY --from=<name> <src> <dest>  # copy files from the build stage
    ```

- **example:**
    ```dockerfile
    # first stage: build the Java application using Maven
    FROM maven:3.8.6-openjdk-17 AS builder
    WORKDIR /app
    
    # copy the project files and build the application
    COPY pom.xml .
    COPY src ./src
    RUN mvn clean package -DskipTests
    
    # second stage: create a lightweight runtime image
    FROM openjdk:17-jdk-slim
    WORKDIR /app
    
    # copy only the built JAR file from the builder stage
    COPY --from=builder /app/target/myapp.jar myapp.jar
    
    # command to run the application
    CMD ["java", "-jar", "myapp.jar"]
    ```
