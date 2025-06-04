# Shell

- you can reference environment variables inside the Compose file using the `${VARIABLE_NAME}` syntax:

    ```yaml
    services:
      web:
        image: myapp:${TAG}
        ports:
          - "${WEB_PORT}:80"
    ```
- these variables come from your local shell environment;

**Shell variables are not picked automatically:**

- this won’t work, `TAG` and `WEB_PORT` will not be recognized in `docker-compose.yml`:

    ```commandline
    export TAG=1.0
    export WEB_PORT=8080
    docker-compose up
    ```

- you have to pass the variable directly inline running the stack:

    ```commandline
    TAG=1.0 WEB_PORT=8080 docker-compose up
    ```
