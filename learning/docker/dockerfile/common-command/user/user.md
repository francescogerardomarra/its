# `USER`

- **purpose:**
  - specifies which **user** the container should run commands as, instead of the default `root`;
  - helps improve **security** by preventing unauthorized access to system files;
  - can be used with **pre-existing users** or new ones created with `RUN useradd`;
  - after this command, all subsequent instructions in the Dockerfile will be executed as the specified user, unless changed later.

- **syntax:**

    ```dockerfile
    USER <username>[:<group>]
    USER <UID>[:<GID>]
    ```

- **example:**
  - **run the container as a non-root user:**
  
    ```dockerfile
    USER appuser
    ```
  - **use a specific UID and GID:**
  
    ```dockerfile
    USER 1001:1001
    ```
  - **create a user before switching to it:**
  
    ```dockerfile
    RUN useradd -m appuser
    USER appuser
    ```
