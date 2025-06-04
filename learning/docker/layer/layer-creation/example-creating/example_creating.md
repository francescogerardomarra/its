# Example of Creating Layers

- consider you build an image from this `Dockerfile`:

    ```dockerfile
    FROM ubuntu
    RUN apt-get update && apt-get install -y curl
    COPY . /app
    ```
- Docker builds it in steps (layers):
  1. base layer: `FROM ubuntu`
     - adds the whole Ubuntu filesystem, usually pulled from Docker Hub;
     - **captures all those changes in a new tar archive**.
  2. next layer: `RUN apt-get ...`
     - executes the command, changes the filesystem (adds curl, modifies some config);
     - **captures all those changes in a new tar archive**.
  3. next layer: `COPY . /app`
     - copies your local project into `/app`;
     - **captures all those changes in a new tar archive**.