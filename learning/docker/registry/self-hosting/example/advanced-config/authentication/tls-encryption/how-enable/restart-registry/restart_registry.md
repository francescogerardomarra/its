# Restart the Docker Registry

- after updating the `config.yml` file, restart your Docker registry to ensure the new configuration takes effect;
- if you're using Docker Compose, run the following commands: // todo: link to docker compose

    ```bash
    docker-compose down
    docker-compose up -d
    ```

- this step is crucial because changes to the `config.yml` file are only applied when the registry is restarted;


- once restarted, the registry will serve requests over HTTPS using the configured certificate and key.
