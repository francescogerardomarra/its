# `CMD`

- **purpose:**
  - defines the default command that runs when a container starts;
  - can be overridden by passing a command during `docker run`;
  - usually used to specify the main process of the container.

- **syntax:**

    ```dockerfile
    # Preferred JSON format
    CMD ["executable", "param1", "param2"]  
    
    # Shell form (less recommended)
    CMD command param1 param2               
    ```

- **JSON VS shell:**
  - **JSON format (`["executable", "param1", "param2"]`)** runs the command **directly as an executable**, without spawning a shell, the process is executed directly by the operating system, without being wrapped inside a shell;
  - **shell form (`CMD command param1 param2`)** runs the command inside a shell (`/bin/sh -c` by default); this allows shell features like variable expansion (`$VAR`), but adds overhead;
  - if using the shell form and the base image **doesn't have `/bin/sh`**, the container may fail to start;
  - the JSON format is recommended when the command doesn’t require shell-specific features, as it is more reliable and avoids unintended behavior.

- **example:**
  - run a Python script by default:
    ```dockerfile
    CMD ["python", "app.py"]
    ```
  - start a Nginx server:
    ```dockerfile
    CMD ["nginx", "-g", "daemon off;"]
    ```
  - override CMD at runtime (running `echo "Hello, world!"` command):
    ```sh
    docker run my-container echo "Hello, world!"
    ```
