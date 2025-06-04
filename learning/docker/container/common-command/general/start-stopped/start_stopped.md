# Start a Stopped Container

- the following command starts an existing, stopped Docker container by its name or ID:

    ```bash
    docker start <container_name|container_id>
    ```

- executed command:

    ```bash
    docker start dc1fc7b84b53
    ```

- output:

    ```bash
    dc1fc7b84b53
    ```

- list running containers:

    ```bash
    CONTAINER ID   IMAGE        COMMAND                  CREATED          STATUS             PORTS                                NAMES
    178cabd7a81e   httpd        "httpd-foreground"       28 minutes ago   Up 6 minutes       80/tcp                               sleepy_shamir
    46fbfb5d5e8d   httpd        "httpd-foreground"       28 minutes ago   Up 21 minutes      80/tcp                               dreamy_matsumoto
    45f6798e8c9b   httpd        "httpd-foreground"       28 minutes ago   Up 21 minutes      80/tcp                               pedantic_payne
    dc1fc7b84b53   httpd        "httpd-foreground"       28 minutes ago   Up 3 seconds       80/tcp                               unruffled_pare
    3dc0044aed4b   registry:2   "/entrypoint.sh /etc…"   16 months ago    Up About an hour   5000/tcp, 0.0.0.0:32000->32000/tcp   registry
    ```

- `docker start` always starts the container in detached mode, regardless of whether the container was previously started in attached mode or detached mode.
