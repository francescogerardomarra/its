# How It Works (With Example)

- you can create a named volume manually:

    ```commandline
    docker volume create my_volume
    ```

- **or Docker will create one automatically if you reference a volume name that doesn't exist when you run a container**;
- using it with a container:

    ```commandline
    docker run -v my_volume:/data my_image
    ```


- `my_volume` is the volume name (host side);
- `/data` is the path inside the container where the volume will be mounted;
- inside the container, it will look like a normal directory, but the data will be stored in the Docker volume;


- ways to create named volumes:
  - [Dockerfile](../../../../create/automatically/dockerfile/dockerfile.md);
  - [container initialization](../../../../create/automatically/container/container.md) (the above example);
  - [`docker-compose.yml` file](../../../../create/automatically/compose/compose.md).
