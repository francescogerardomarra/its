# Docker Run VS Docker Start

**`docker run`**
- **purpose**: creates and starts a new container;
- **when to use**: use this command when you want to create a container from an image and start it immediately;
- **key characteristics**:
  - combines multiple actions: creates a container, allocates resources, and starts it;
  - accepts a variety of options (`-d`, `-it`, `--name`, `-v`, `-p`, etc.) to configure the container's runtime behavior;
  - example:
    ```bash
    docker run -it ubuntu bash
    ```
    - pulls the `ubuntu` image (if not already available locally).
    - creates a new container using the image.
    - starts the container and runs the `bash` shell interactively.

- **creates a new instance**: each time you run `docker run`, a new container is created even if you use the same image.

**`docker start`**
- **purpose**: starts an existing, stopped container;
- **when to use**: use this command when you already have a container that was stopped, and you want to start it again;
- **key characteristics**:
    - only starts containers that already exist;
    - does not allow configuring options like `-it` or port mapping because the container configuration is already set;
    - example:
      ```bash
      docker start my_container
      ```
      - starts the container named `my_container`.

- **does not create a new container**: unlike `docker run`, this does not create a new instance.
