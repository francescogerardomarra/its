# `env_file`

- instead of defining variables inline, you can load them from a separate file:

    ```yaml
    services:
      app:
        image: myapp
        env_file:
          - app.env
    ```
- how `app.env` might contain:

    ```yaml
    TAG=1.0
    WEB_PORT=8080
    ```
