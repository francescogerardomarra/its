# `docker compose build`

- this command is used to build the images defined in your `docker-compose.yml` file;
- if you don’t specify a service, it will build all services;
- if you specify one or more services, it will build only those;


- it rebuilds the image according to the [Dockerfile](../../../../dockerfile/index.md), but only if needed, if neither the `Dockerfile` nor any files in the build context have changed since the last build, Docker will use the cached image and skip rebuilding;


**Example:**

- `docker-compose.yml` file:

    ```yaml
    services:
      web:
        build: ./web
      api:
        build: ./api
    ```
- command to run:

    ```commandline
    docker compose build
    ```
- outcome:
  - it goes to the `./web` directory and builds an image using the `Dockerfile` there;
  - it goes to the `./api` directory and builds an image using the `Dockerfile` there.
