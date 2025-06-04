# Run with Automatic Port Mapping

- the following command is used to **start a new container from an image** and **publish all exposed ports** (it's possible to expose more than one port) to random ports on your host machine:

    ```bash
    docker run -P <image>
    ```

  - `docker run`: this is the basic command to **create and start a new Docker container**;
  - `-P` (uppercase `P`): this tells Docker to **publish all the ports** that the image has declared with the `EXPOSE` instruction in its Dockerfile, **mapping them to random available ports on the host**.

### Example:

- suppose you have an image that exposes port `80`, like this in its Dockerfile:

    ```dockerfile
    EXPOSE 80
    ```

- if you run:

    ```bash
    docker run -P my-image
    ```

- Docker will do something like:

    ```bash
    0.0.0.0:32768 -> 80/tcp
    ```

- this means:
  - port `80` inside the container is mapped to port `32768` on your host;
  - so, if the container runs a web server on port `80`, you can access it via `http://localhost:32768`.
- to see which port was assigned, run:

    ```bash
    docker ps
    ```

- you’ll see output like:

    ```bash
    CONTAINER ID   IMAGE       PORTS
    abcd1234       my-image    0.0.0.0:32768->80/tcp
    ```
