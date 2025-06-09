# `ENV` (Environment Variables)

- **purpose:**
  - defines **environment variables** that persist throughout the container’s lifecycle;
  - allows configuration values to be **set once** and used across multiple commands;
  - can be overridden at runtime using the `-e` flag in `docker run`.

- **syntax:**

    ```dockerfile
    ENV <variable_name> <value>
    or
    ENV <variable_name>=<value>
    ```

- **example:**
  - **set an environment variable inside the container:**
  
    ```dockerfile
    ENV APP_ENV production
    ```
  - **use the variable in subsequent commands:**
  
    ```dockerfile
    ENV APP_DIR /app
    WORKDIR $APP_DIR
    ```
  - **override the variable at runtime:**
  
    ```sh
    docker run -e APP_ENV=development my-container
    ```

- when use the `=` ?

  - **`ENV <variable_name> <value>` (space-separated format)**: 
    - this is the **preferred syntax** in Dockerfiles, as it avoids issues with special characters and maintains better readability;
    - example:
    
      ```dockerfile
      ENV APP_NAME "My Application"
      ```  

  - **`ENV <variable_name>=<value>` (equal sign format)**: 
    - while still valid, this format is **less common** in Dockerfiles and is more commonly seen in shell scripts;
    - it may lead to unexpected behavior if the value contains spaces, since Docker might not handle spaces properly;
    - example:
    
      ```dockerfile
      ENV APP_NAME="My Application"
      ```  
