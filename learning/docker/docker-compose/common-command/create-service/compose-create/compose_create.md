# `docker compose create`

- this command creates the containers defined in your `docker-compose.yml` file, but does **not** start them;
- this is useful if you want to set things up in advance before actually running the containers;
- it builds the image if needed;


- if the image is already built, even if `Dockerfile` or app code changes it will not rebuild the image;
- to rebuild the images in case of changes use the `--build` flag:

    ```commandline
    docker compose create --build
    ```

**Example:**

These commands must be running within the folder containing the `docker-compose.yml` file:

- create containers for all services:

    ```commandline
    docker compose create
    ```

- create containers for specific services:

    ```commandline
    docker compose create web db
    ```
