# `ENTRYPOINT`

- **purpose:**
  - defines the main executable that always runs when the container starts;
  - unlike [CMD](../cmd/cmd.md), it **cannot be overridden** with `docker run <container> <command>`;
  - used to create containers that act like executables, accepting **additional** arguments at runtime;
  - during the `docker run` command, any additional arguments you provide are **appended** to the `ENTRYPOINT` command instead of replacing it.

- **syntax:**

    ```dockerfile
    # Preferred JSON format
    ENTRYPOINT ["executable", "param1", "param2"] 
  
    # Shell form (less recommended)
    ENTRYPOINT command param1 param2               
    ```

- **example:**
  - set a script as the container's main process:
    ```dockerfile
    ENTRYPOINT ["/app/start.sh"]
    ```
  - run a command with default parameters:
    ```dockerfile
    ENTRYPOINT ["python", "app.py"]
    ```
    - pass additional arguments at runtime:
      ```sh
      docker run my-container --debug
      ```
    - the final command inside the container will be:
      ```sh
      python app.py --debug
      ```
