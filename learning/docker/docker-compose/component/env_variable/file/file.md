# File

- you can reference environment variables inside the Compose file using the `${VARIABLE_NAME}` syntax:

    ```yaml
    services:
      web:
        image: myapp:${TAG}
        ports:
          - "${WEB_PORT}:80"
    ```
- these variables come from an `.env` file in the same directory as `docker-compose.yml`;

**File variables are picked automatically:**

- when you run `docker compose up`, it will substitute `TAG` and `WEB_PORT` correctly, no extra steps needed (unlike with [shell](../shell/shell.md) env variables).
- `.env` file:

  ```dotenv
  TAG=1.0
  WEB_PORT=8080
  ```
