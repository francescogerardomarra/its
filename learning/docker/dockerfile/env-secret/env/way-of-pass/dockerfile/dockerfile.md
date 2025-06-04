# Using `Dokerfile`

- the `ENV` instruction sets default environment variables in a `Dockerfile`;
- these variables are available during the build and runtime of the container;
- during the build means that they can be used by other `Dockerfile` commands:

    ```dockerfile
    ENV APP_DIR=/usr/src/app
    WORKDIR $APP_DIR
    ```


- they can be overridden by `.env` files or the `-e` flag in `docker run`;
- this ensures default values are set if no external variables are provided.

**Example `Dockerfile` file:**

```dockerfile
ENV DB_HOST=localhost
ENV DB_USER=root
```
