# Where Are Built Images Stored? Definition

- images built by Docker Compose are stored locally on your machine, specifically in the Docker daemon’s image storage;
- you can see it by running:

    ```commandline
    docker images
    ```

- the actual files are stored in a Docker-managed location on your filesystem (`/var/lib/docker`).
