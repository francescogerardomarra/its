# Environment Variables 

- an environment variable is a key-value pair used by an operating system or application to store configuration settings;
- instead of hardcoding values in the code, environment variables provide a flexible way to configure applications dynamically;
- users can dynamically change values at runtime.

**Example:**
- set an environment variable for the current session on linux, from shell:

    ```commandline
    export APP_ENV=production
    ```

- set an environment variable within the container:

    ```commandline
    docker run -e APP_ENV=production my_image
    ```

- set an environment variable within the `Dockerfile`, that will be available in the running container:

    ```dockerfile
    ENV APP_ENV production
    ```
