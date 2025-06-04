# Restart a Container

- the following command stops and then immediately restarts a specified container:

   ```bash
   docker restart <container_name|container_id>
   ```

- executed commands:

    ```bash
    docker start dc1fc7b84b53
    docker restart dc1fc7b84b53
    ```

- output:

    ```bash
    dc1fc7b84b53
    ```
  
- list running containers:

    ```bash
    CONTAINER ID   IMAGE        COMMAND                  CREATED          STATUS             PORTS                                NAMES
    178cabd7a81e   httpd        "httpd-foreground"       32 minutes ago   Up 10 minutes      80/tcp                               sleepy_shamir
    46fbfb5d5e8d   httpd        "httpd-foreground"       32 minutes ago   Up 25 minutes      80/tcp                               dreamy_matsumoto
    45f6798e8c9b   httpd        "httpd-foreground"       32 minutes ago   Up 25 minutes      80/tcp                               pedantic_payne
    dc1fc7b84b53   httpd        "httpd-foreground"       32 minutes ago   Up 2 seconds       80/tcp                               unruffled_pare
    3dc0044aed4b   registry:2   "/entrypoint.sh /etc…"   16 months ago    Up About an hour   5000/tcp, 0.0.0.0:32000->32000/tcp   registry
    ```
