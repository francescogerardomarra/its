# `docker compose config --volumes`

- this command works like [docker compose config](../compose-config/compose_config.md) but the `--volumes` flag tells Docker to only output the names of the volumes defined in the configuration;
- **it shows only the volumes used by at least one service.**

**Example:**

- `docker-compose.yml` file:

    ```yaml
    version: '3.8'
    services:
      web:
        image: nginx
      db:
        image: postgres
  
    volumes:
      my-volume-one:
      my-volume-two:
    ```

- open a terminal within the above `docker-compose.yml` folder and run this command:

    ```commandline
    docker compose config --volumes
    ```

- output:

    ```commandline
    my-volume-one
    ```
  
    as you can see, just `my-volume-one` is shown since `my-volume-two` isn't used by any service.
