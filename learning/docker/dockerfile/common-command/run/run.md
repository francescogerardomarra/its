# `RUN`

- **purpose:**
  - executes a command **during the image build process** and creates a new layer in the image;
  - typically used to install packages, configure settings, or perform setup tasks;
  - commands executed with `RUN` **do not persist at runtime**, only their effects (e.g., installed software).

- **syntax:**

    ```dockerfile
    RUN <command>        # Shell form (executes inside /bin/sh -c)
    RUN ["executable", "arg1", "arg2"]  # JSON format (direct execution)
    ```

  see the difference between JSON and shell execution [here](../cmd/cmd.md).

- **example:**
  - **Install system packages:**
    ```dockerfile
    RUN apt-get update && apt-get install -y curl
    ```
  - **Create a directory and set permissions:**
    ```dockerfile
    RUN mkdir /app && chmod 755 /app
    ```
  - **Use JSON format to avoid a shell (recommended for reliability):**
    ```dockerfile
    RUN ["mkdir", "/app"]
    ```
