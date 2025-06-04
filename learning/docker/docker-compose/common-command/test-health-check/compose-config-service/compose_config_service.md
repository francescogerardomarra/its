# `docker compose config --services`

This command works like [docker compose config](../compose-config/compose_config.md) but the `--services` flag tells Docker to only output the names of the services defined in the configuration.

**Example:**

- `docker-compose.yml` file:

    ```yaml
    version: '3.8'
    services:
      web:
        image: nginx
      db:
        image: postgres
    ```

- open a terminal within the above `docker-compose.yml` folder and run this command:

    ```commandline
    docker compose config --services
    ```

- output:

    ```commandline
    db
    web
    ```
