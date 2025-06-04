# Single Command

- The following command is used to execute a command inside a running Docker container:

    ```bash
    docker exec <container_name|container_id> <command>
    ```

- executed commands:

    ```bash
    docker run --name my-container redis
    docker exec my-container ls /
    ```

- command overview:
  - the command executed is `ls /`, which lists the content of root container folder.

- output:

    ```bash
    bin
    boot
    data
    dev
    etc
    home
    lib
    lib64
    media
    mnt
    opt
    proc
    root
    run
    sbin
    srv
    sys
    tmp
    usr
    var
    ```

- list running containers:

    ```bash
    CONTAINER ID   IMAGE        COMMAND                  CREATED         STATUS         PORTS                                NAMES
    18f44ce5003f   redis        "docker-entrypoint.s…"   2 minutes ago   Up 2 minutes   6379/tcp                             my-container
    f6f175294090   nginx        "/docker-entrypoint.…"   4 hours ago     Up 4 hours     80/tcp                               intelligent_lewin
    45f6798e8c9b   httpd        "httpd-foreground"       5 hours ago     Up 5 hours     80/tcp                               pedantic_payne
    3dc0044aed4b   registry:2   "/entrypoint.sh /etc…"   16 months ago   Up 6 hours     5000/tcp, 0.0.0.0:32000->32000/tcp   registry
    ```
