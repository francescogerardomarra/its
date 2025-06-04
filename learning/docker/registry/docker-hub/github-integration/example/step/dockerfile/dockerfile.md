# `Dockerfile`

- in this chapter, we show the `Dockerfile` present within the [Maven project](../maven-project/maven_project.md);
- each command is explained with a comment.

**`Dockerfile` of Maven Project:**

```dockerfile
# Base image for Java 8
FROM openjdk:8-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from your target directory into the container
# Assuming the built JAR is in the "target" directory of your Maven project
COPY target/docker-course-test.jar app.jar

# Command to run the JAR file
CMD ["java", "-jar", "app.jar"]
```
