# `Dockerfile`

- this is the Dockerfile used for the [maven project](../maven-project/download/download.md):

    ```dockerfile
    FROM openjdk:17-alpine
    WORKDIR /app
    COPY target/secret-kubernetes.jar ./
    CMD ["java", "-jar", "secret-kubernetes.jar"]
    ```
- it copies the artifact within the image and executes it once a container will be instantiated from the image.
