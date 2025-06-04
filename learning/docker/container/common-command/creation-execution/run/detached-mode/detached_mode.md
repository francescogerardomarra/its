# Run in Detached Mode

- the following command is used to create and start a new Docker container from a specified Docker image, running it in detached mode;
- it means it runs the container in the background, detached from the terminal;
- this is commonly used for long-running services like web servers:

    ```bash
    docker run -d <image_name>
    ```

- executed command:

    ```bash
    docker run -d redis
    ```

- output:

    ```bash
    Unable to find image 'redis:latest' locally
    latest: Pulling from library/redis
    2d429b9e73a6: Already exists 
    92ef1eccbb9f: Pull complete 
    5e00ad97561c: Pull complete 
    8f865c3d417c: Pull complete 
    74c736b00471: Pull complete 
    928f5dbb5007: Pull complete 
    4f4fb700ef54: Pull complete 
    6fd0c1bf3b91: Pull complete 
    Digest: sha256:af0be38eb8e43191bae9b03fe5c928803930b6f93e2dde3a7ad1165c04b1ce22
    Status: Downloaded newer image for redis:latest
    342998e4eec592ac05b398853afc2cbd53217e223a91c0d1d174e245e8d50b68
    ```

- list running containers:

    ```bash
    CONTAINER ID   IMAGE        COMMAND                  CREATED          STATUS          PORTS                                NAMES
    342998e4eec5   redis        "docker-entrypoint.s…"   17 seconds ago   Up 17 seconds   6379/tcp                             ecstatic_leakey
    f6f175294090   nginx        "/docker-entrypoint.…"   25 minutes ago   Up 24 minutes   80/tcp                               intelligent_lewin
    46fbfb5d5e8d   httpd        "httpd-foreground"       2 hours ago      Up 2 hours      80/tcp                               dreamy_matsumoto
    45f6798e8c9b   httpd        "httpd-foreground"       2 hours ago      Up 2 hours      80/tcp                               pedantic_payne
    3dc0044aed4b   registry:2   "/entrypoint.sh /etc…"   16 months ago    Up 3 hours      5000/tcp, 0.0.0.0:32000->32000/tcp   registry
    ```

- note: when the container starts, it runs in the background, freeing up the terminal, so you can continue to use that terminal to run other commands.
