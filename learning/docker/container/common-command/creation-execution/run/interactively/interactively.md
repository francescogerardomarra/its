# Run Interactively

- the following is used to start a new container from a Docker image in an interactive mode;
- `-it`: are two flags combined to enable interactive mode:
  - `-i` (**interactive**): keeps the standard input (stdin) of the container open, even if you're not actively using it;
  - without `-i` the container might start, but you won't be able to interact with it via commands from your terminal;
  - `-t` (**tty**): allocates a pseudo-TTY (terminal) to the container, enabling you to interact with it as if you were using a normal terminal session;
  - without `-t` even if you can send input (via `-i`), the interaction might not behave like a proper terminal;
  - for example, the shell won't format outputs nicely or handle command-line editing properly.

    ```bash
    docker run -it <image_name>
    ```

- executed command:

    ```bash
    docker run -it ubuntu
    ```

- output:

    ```bash
    Unable to find image 'ubuntu:latest' locally
    latest: Pulling from library/ubuntu
    afad30e59d72: Already exists 
    Digest: sha256:278628f08d4979fb9af9ead44277dbc9c92c2465922310916ad0c46ec9999295
    Status: Downloaded newer image for ubuntu:latest
    root@d6465b3873ae:/# 
    ```

- list running containers:

    ```bash
    CONTAINER ID   IMAGE        COMMAND                  CREATED          STATUS          PORTS                                NAMES
    d6465b3873ae   ubuntu       "/bin/bash"              31 seconds ago   Up 30 seconds                                        stoic_wing
    9b4f7b6c4b92   python       "python3"                6 minutes ago    Up 6 minutes                                         my-container-3
    342998e4eec5   redis        "docker-entrypoint.s…"   3 hours ago      Up 3 hours      6379/tcp                             ecstatic_leakey
    f6f175294090   nginx        "/docker-entrypoint.…"   3 hours ago      Up 3 hours      80/tcp                               intelligent_lewin
    46fbfb5d5e8d   httpd        "httpd-foreground"       5 hours ago      Up 5 hours      80/tcp                               dreamy_matsumoto
    45f6798e8c9b   httpd        "httpd-foreground"       5 hours ago      Up 5 hours      80/tcp                               pedantic_payne
    3dc0044aed4b   registry:2   "/entrypoint.sh /etc…"   16 months ago    Up 6 hours      5000/tcp, 0.0.0.0:32000->32000/tcp   registry
    ```

- executed command within container:

    ```bash
    Unable to find image 'ubuntu:latest' locally
    latest: Pulling from library/ubuntu
    afad30e59d72: Already exists 
    Digest: sha256:278628f08d4979fb9af9ead44277dbc9c92c2465922310916ad0c46ec9999295
    Status: Downloaded newer image for ubuntu:latest
    root@d6465b3873ae:/# ls -l
    total 48
    lrwxrwxrwx   1 root root    7 Apr 22  2024 bin -> usr/bin
    drwxr-xr-x   2 root root 4096 Apr 22  2024 boot
    drwxr-xr-x   5 root root  360 Nov 28 14:38 dev
    drwxr-xr-x   1 root root 4096 Nov 28 14:38 etc
    drwxr-xr-x   3 root root 4096 Oct 16 07:00 home
    lrwxrwxrwx   1 root root    7 Apr 22  2024 lib -> usr/lib
    lrwxrwxrwx   1 root root    9 Apr 22  2024 lib64 -> usr/lib64
    drwxr-xr-x   2 root root 4096 Oct 16 06:53 media
    drwxr-xr-x   2 root root 4096 Oct 16 06:53 mnt
    drwxr-xr-x   2 root root 4096 Oct 16 06:53 opt
    dr-xr-xr-x 457 root root    0 Nov 28 14:38 proc
    drwx------   2 root root 4096 Oct 16 07:00 root
    drwxr-xr-x   4 root root 4096 Oct 16 07:00 run
    lrwxrwxrwx   1 root root    8 Apr 22  2024 sbin -> usr/sbin
    drwxr-xr-x   2 root root 4096 Oct 16 06:53 srv
    dr-xr-xr-x  13 root root    0 Nov 28 14:38 sys
    drwxrwxrwt   2 root root 4096 Oct 16 07:00 tmp
    drwxr-xr-x  12 root root 4096 Oct 16 06:53 usr
    drwxr-xr-x  11 root root 4096 Oct 16 07:00 var
    root@d6465b3873ae:/# 
    ```
