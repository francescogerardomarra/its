# Example

In this example, we show a Compose service that uses a Dockerfile to build the image.

- `docker-compose.yml`:

  ```yaml
  services:
    web:
      build: .
      image: compose-built-image:1.0
  ```

- `Dockerfile`:

  ```dockerfile
  FROM python:3.11-slim
  
  CMD ["python", "-c", "print('Hello from Docker!')"]
  ```

- build the image using Docker Compose:

  ```commandline
  docker compose build
  ```

- check if the image is present on your machine:

  ```commandline
  docker images
  ```
- output:

  ```commandline
  REPOSITORY                       TAG           IMAGE ID       CREATED         SIZE
  compose-built-image              1.0           9678d2d1a064   4 days ago      130MB
  ```
