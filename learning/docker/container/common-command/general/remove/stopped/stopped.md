# Remove a Stopped Container

**Remove a stopped container:**

- this command removes a stopped container from Docker:

   ```bash
   docker rm <container_name|container_id>
   ```

- executed commands:

    ```bash
    docker stop dc1fc7b84b53
    docker rm dc1fc7b84b53
    ```

- output:

    ```bash
    dc1fc7b84b53
    ```

- list all containers:

    ```bash
    CONTAINER ID   IMAGE                                        COMMAND                  CREATED          STATUS                       PORTS                                NAMES
    5ebfbb3ec809   httpd                                        "httpd-foreground"       32 minutes ago   Exited (0) 32 minutes ago                                         festive_vaughan
    178cabd7a81e   httpd                                        "httpd-foreground"       39 minutes ago   Up 17 minutes                80/tcp                               sleepy_shamir
    46fbfb5d5e8d   httpd                                        "httpd-foreground"       39 minutes ago   Up 33 minutes                80/tcp                               dreamy_matsumoto
    45f6798e8c9b   httpd                                        "httpd-foreground"       39 minutes ago   Up 32 minutes                80/tcp                               pedantic_payne
    5b21be3df384   postgres                                     "docker-entrypoint.s…"   41 minutes ago   Exited (1) 12 minutes ago                                         hungry_ptolemy
    ```

- note: if you try to remove the running container before stopping it, you'll have this error:

    ```bash
    Error response from daemon: cannot remove container "/unruffled_pare": container is running: stop the container before removing or force remove
    ```
