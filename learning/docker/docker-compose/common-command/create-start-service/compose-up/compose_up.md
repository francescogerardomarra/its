# `docker compose up`

This command reads the `docker-compose.yml` file in your current directory and does the following:

- build images (if they aren’t already built);
- create containers for each service defined in the file;
- start those containers;


- attaches the logs to your terminal, so you can see output from all services in real-time.

**Example:**

- `docker-compose.yml` file:

    ```commandline
    services:
      web:
        build: .
        image: myapp:1.0
    ```

- command to run:

    ```commandline
    docker compose up
    ```

- outcome: 
  - it will build the image using the `Dockerfile` in the current directory (**just if the image isn't already built**);
  - if the image is already built, even if you change the `Dockerfile`, **it will not build the image again**;
  - it will tag the image using the specified tag `myapp:1.0`.
