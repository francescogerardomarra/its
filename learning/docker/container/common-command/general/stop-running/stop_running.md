# Stop a Running Container

- the following command stops a running container by sending a termination signal, allowing for graceful shutdown:

   ```bash
   docker stop <container_name|container_id>
   ```

  - this command **sends a `SIGTERM` signal** to the main process inside the container, giving it a chance to shut down cleanly;
  - if the process does not stop within a default timeout (usually 10 seconds), Docker then sends a `SIGKILL` to forcefully terminate it.

- executed command:

    ```bash
    docker stop dc1fc7b84b53
    ```

- output:

    ```bash
    dc1fc7b84b53
    ```

- list running containers:

    ```bash
    CONTAINER ID   IMAGE        COMMAND                  CREATED          STATUS             PORTS                                NAMES
    178cabd7a81e   httpd        "httpd-foreground"       30 minutes ago   Up 9 minutes       80/tcp                               sleepy_shamir
    46fbfb5d5e8d   httpd        "httpd-foreground"       31 minutes ago   Up 24 minutes      80/tcp                               dreamy_matsumoto
    45f6798e8c9b   httpd        "httpd-foreground"       31 minutes ago   Up 24 minutes      80/tcp                               pedantic_payne
    3dc0044aed4b   registry:2   "/entrypoint.sh /etc…"   16 months ago    Up About an hour   5000/tcp, 0.0.0.0:32000->32000/tcp   registry
    ```
