# `environment`

- this sets environment variables inside the container of service `app`:

    ```yaml
    services:
      app:
        image: myapp
        environment:
          - NODE_ENV=production
          - API_KEY=AIzaSyCwEro-wQ6YUNcA1ozA9FQev-DyJp3t2EQ
    ```

- you can mix hardcoded values and environment variable references (like [shell](../../shell/shell.md) or [file](../../file/file.md)):

    ```yaml
    services:
      app:
        image: myapp
        environment:
          - NODE_ENV=production
          - API_KEY=${MY_API_KEY}
    ```
