# `docker compose logs`

- this command is used to view the logs (output) of the services defined in your `docker-compose.yml` file;
- it shows the stdout (standard output) and stderr (standard error) of all containers managed by Docker Compose;
- it helps you debug or monitor what's happening in your running containers.

**Example:**

- for all services:

    ```commandline
    docker compose logs
    ```

- for a specific service:

    ```commandline
    docker compose logs web
    ```

- keep streaming the logs as they update in real-time:

    ```commandline
    docker compose logs -f
    ```

- shows only the last 100 lines of logs:

    ```commandline
    docker compose logs --tail=100
    ```
