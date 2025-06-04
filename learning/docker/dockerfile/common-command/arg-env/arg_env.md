# `ARG` and `ENV`

- **purpose:**
  - `ARG` defines build-time variables that are only available during the image build process;
  - `ENV` sets environment variables that persist in the container at runtime;
  - `ENV` can use `ARG` values, but `ARG` is not available once the container is running;
  - `ENV` variables can be overridden at runtime using the `-e` flag with `docker run` command;
  - the `-e` flag **cannot** be used with `docker start` command.

- **syntax:**

    ```dockerfile
    # Define a build-time variable
    ARG <variable_name>[=<default_value>]
    
    # Define an environment variable
    ENV <variable_name> <value>
    ```

- **example:**
  - define an `ARG` and use it in an `ENV` variable:
  
    ```dockerfile
    ARG APP_PORT=8080
    ENV PORT $APP_PORT
    ```
  - set an `ENV` variable directly:
  
    ```dockerfile
    ENV APP_ENV production
    ```

    with `=` also valid but less common:

    ```dockerfile
    ENV APP_ENV=production
    ```

  - use an `ARG` in a command (only available at build time):
  
    ```dockerfile
    ARG BASE_IMAGE=ubuntu:latest
    FROM $BASE_IMAGE
    ```
