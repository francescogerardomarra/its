# Run in Attached Mode (Default)

- the following command is used to create and start a new container from a specified Docker image;
- the container runs in the foreground (attached mode), meaning logs and output from the container are displayed in your terminal;
- pressing `ctrl+c` sends an interrupt signal (`SIGINT`) to the main process inside the container, causing it to terminate.

    ```bash
    docker run <image_name>
    ```

- executed command:

    ```bash
    docker run nginx
    ```

- output:

    ```bash
    Unable to find image 'nginx:latest' locally
    latest: Pulling from library/nginx
    2d429b9e73a6: Already exists 
    20c8b3871098: Pull complete 
    06da587a7970: Pull complete 
    f7895e95e2d4: Pull complete 
    7b25f3e99685: Pull complete 
    dffc1412b7c8: Pull complete 
    d550bb6d1800: Pull complete 
    Digest: sha256:0c86dddac19f2ce4fd716ac58c0fd87bf69bfd4edabfd6971fb885bafd12a00b
    Status: Downloaded newer image for nginx:latest
    /docker-entrypoint.sh: /docker-entrypoint.d/ is not empty, will attempt to perform configuration
    /docker-entrypoint.sh: Looking for shell scripts in /docker-entrypoint.d/
    /docker-entrypoint.sh: Launching /docker-entrypoint.d/10-listen-on-ipv6-by-default.sh
    10-listen-on-ipv6-by-default.sh: info: Getting the checksum of /etc/nginx/conf.d/default.conf
    10-listen-on-ipv6-by-default.sh: info: Enabled listen on IPv6 in /etc/nginx/conf.d/default.conf
    /docker-entrypoint.sh: Sourcing /docker-entrypoint.d/15-local-resolvers.envsh
    /docker-entrypoint.sh: Launching /docker-entrypoint.d/20-envsubst-on-templates.sh
    /docker-entrypoint.sh: Launching /docker-entrypoint.d/30-tune-worker-processes.sh
    /docker-entrypoint.sh: Configuration complete; ready for start up
    2024/11/28 11:11:40 [notice] 1#1: using the "epoll" event method
    2024/11/28 11:11:40 [notice] 1#1: nginx/1.27.3
    2024/11/28 11:11:40 [notice] 1#1: built by gcc 12.2.0 (Debian 12.2.0-14) 
    2024/11/28 11:11:40 [notice] 1#1: OS: Linux 6.8.0-49-generic
    2024/11/28 11:11:40 [notice] 1#1: getrlimit(RLIMIT_NOFILE): 1048576:1048576
    2024/11/28 11:11:40 [notice] 1#1: start worker processes
    2024/11/28 11:11:40 [notice] 1#1: start worker process 29
    2024/11/28 11:11:40 [notice] 1#1: start worker process 30
    2024/11/28 11:11:40 [notice] 1#1: start worker process 31
    2024/11/28 11:11:40 [notice] 1#1: start worker process 32
    2024/11/28 11:11:40 [notice] 1#1: start worker process 33
    2024/11/28 11:11:40 [notice] 1#1: start worker process 34
    2024/11/28 11:11:40 [notice] 1#1: start worker process 35
    2024/11/28 11:11:40 [notice] 1#1: start worker process 36
    ```

- list running containers (in another terminal):

    ```bash
    CONTAINER ID   IMAGE        COMMAND                  CREATED         STATUS         PORTS                                NAMES
    035848ef2c28   nginx        "/docker-entrypoint.…"   8 seconds ago   Up 7 seconds   80/tcp                               wizardly_liskov
    46fbfb5d5e8d   httpd        "httpd-foreground"       2 hours ago     Up 2 hours     80/tcp                               dreamy_matsumoto
    45f6798e8c9b   httpd        "httpd-foreground"       2 hours ago     Up 2 hours     80/tcp                               pedantic_payne
    3dc0044aed4b   registry:2   "/entrypoint.sh /etc…"   16 months ago   Up 2 hours     5000/tcp, 0.0.0.0:32000->32000/tcp   registry
    ```

