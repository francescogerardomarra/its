# Execute Shell

- the following command is used in Docker to interactively access the shell of a running container;
- if you don't include the `-it` flag:
  - the `/bin/bash` process starts inside the container;
  - since there’s no interactive (`-i`) or terminal (`-t`) mode, you're not connected to the bash process;
  - the `/bin/bash` process might terminate right away because it’s designed to wait for input, which isn’t provided in this case;
  - if it stays running (depending on the setup), you won’t see it or interact with it because no terminal session is allocated.

    ```bash
    docker exec -it <container_name|container_id> /bin/bash
    ```

- executed command:

    ```bash
    docker exec -it my-container /bin/bash
    ```

- output:

    ```bash
    root@18f44ce5003f:/data#
    ```

- list running containers:

    ```bash
    CONTAINER ID   IMAGE        COMMAND                  CREATED          STATUS          PORTS                                NAMES
    18f44ce5003f   redis        "docker-entrypoint.s…"   45 minutes ago   Up 45 minutes   6379/tcp                             my-container
    f6f175294090   nginx        "/docker-entrypoint.…"   4 hours ago      Up 4 hours      80/tcp                               intelligent_lewin
    45f6798e8c9b   httpd        "httpd-foreground"       6 hours ago      Up 6 hours      80/tcp                               pedantic_payne
    3dc0044aed4b   registry:2   "/entrypoint.sh /etc…"   16 months ago    Up 7 hours      5000/tcp, 0.0.0.0:32000->32000/tcp   registry
    ```

- executed command within container:

    ```bash
    root@18f44ce5003f:/data# cd /
    root@18f44ce5003f:/# ls -l
    total 52
    lrwxrwxrwx   1 root  root     7 Nov 11 00:00 bin -> usr/bin
    drwxr-xr-x   2 root  root  4096 Oct 31 11:04 boot
    drwxr-xr-x   2 redis redis 4096 Nov 12 02:37 data
    drwxr-xr-x   5 root  root   340 Nov 28 14:53 dev
    drwxr-xr-x   1 root  root  4096 Nov 28 14:53 etc
    drwxr-xr-x   2 root  root  4096 Oct 31 11:04 home
    lrwxrwxrwx   1 root  root     7 Nov 11 00:00 lib -> usr/lib
    lrwxrwxrwx   1 root  root     9 Nov 11 00:00 lib64 -> usr/lib64
    drwxr-xr-x   2 root  root  4096 Nov 11 00:00 media
    drwxr-xr-x   2 root  root  4096 Nov 11 00:00 mnt
    drwxr-xr-x   2 root  root  4096 Nov 11 00:00 opt
    dr-xr-xr-x 449 root  root     0 Nov 28 14:53 proc
    drwx------   1 root  root  4096 Nov 28 15:27 root
    drwxr-xr-x   3 root  root  4096 Nov 11 00:00 run
    lrwxrwxrwx   1 root  root     8 Nov 11 00:00 sbin -> usr/sbin
    drwxr-xr-x   2 root  root  4096 Nov 11 00:00 srv
    dr-xr-xr-x  13 root  root     0 Nov 28 14:39 sys
    drwxrwxrwt   2 root  root  4096 Nov 11 00:00 tmp
    drwxr-xr-x   1 root  root  4096 Nov 11 00:00 usr
    drwxr-xr-x   1 root  root  4096 Nov 11 00:00 var
    root@18f44ce5003f:/#
    ```
