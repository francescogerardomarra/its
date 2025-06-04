# WORKDIR

- **purpose:**
  - sets the **working directory** for all subsequent `RUN`, `CMD`, `ENTRYPOINT`, and other commands in the Dockerfile;
  - it means all the later commands will be executed within the **working directory**;
  - helps avoid using absolute paths in multiple instructions, improving readability and maintainability;
  - automatically creates the directory if it doesn’t exist.

- **syntax:**

    ```dockerfile
    WORKDIR <path>
    ```

- **example:**
  - **set `/app` as the working directory:**
  
    ```dockerfile
    WORKDIR /app
    ```
  - **ensure files are copied into the correct directory (`.` means `/app` directory):**
  
    ```dockerfile
    WORKDIR /app
    COPY myfile.txt .
    ```
  - **change directories multiple times:**
  
    ```dockerfile
    WORKDIR /app
    # do something...
    WORKDIR logs
    CMD ["ls", "-l"]  # runs in /app/logs
    ```
