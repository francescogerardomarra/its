# Dockerfile

- this is the Dockerfile used for the [maven project](../maven-project/maven_project.md):

    ```dockerfile
    FROM openjdk:17-alpine
    WORKDIR /app
    COPY target/secret-swarm.jar ./
    CMD ["java", "-jar", "secret-swarm.jar"]
    ```
- it copies the artifact within the image and executes it once a container will be instantiated from the image.
