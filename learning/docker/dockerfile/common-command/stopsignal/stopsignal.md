# `STOPSIGNAL`

- **purpose:**
  - `STOPSIGNAL` defines which signal Docker should send to the container's main process (PID 1) when stopping it gracefully using `docker stop` command;
  - helps ensure applications clean up resources properly before shutting down;
  - overrides the default **SIGTERM** signal if a different one is required;
  - a **signal** is a software interrupt sent to a process to notify it of an event (e.g., termination, restart, or pause);
  - the stop signal is **sent when you run `docker stop <container>`**, giving the process time to exit before being forcibly killed;
  - since if the process does not exit within the timeout (default is 10 seconds), Docker forcefully kills it with SIGKILL (kill -9) to ensure it stops.
  
- **syntax:**

  ```dockerfile
  STOPSIGNAL <signal>
  ```

- **example:**
  - **set SIGKILL instead of the default SIGTERM:**
    ```dockerfile
    STOPSIGNAL SIGKILL
    ```
  - **use a numeric signal instead of a name:**
    ```dockerfile
    STOPSIGNAL 9  # Equivalent to SIGKILL
    ```
  - **check the stop signal in an existing container:**
    ```sh
    docker inspect --format='{{.Config.StopSignal}}' my-container
    ```
  - **what is SIGTERM?**
      - SIGTERM (**signal 15**) is the **default termination signal** used by `docker stop`;
      - it allows processes to **gracefully shut down** (e.g., save data, close connections);
      - if the process doesn’t exit within Docker’s timeout (default 10s), Docker sends **SIGKILL (signal 9)** to force-stop it.
