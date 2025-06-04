# Run a Container and Remove It After It Stops

- the following command is used to run a Docker container from the specified `<image_name>` with the `--rm` option;
- which tells Docker to automatically remove the container once it stops;
- this ensures that the container does not persist on your system after it exits, which is useful for temporary or one-off tasks where you don't need to keep the container for later use:

    ```bash
    docker run --rm <image_name>
    ```

- first executed command:

    ```bash
    docker run --rm redis
    ```

- list running containers:

    ```bash
    CONTAINER ID   IMAGE        COMMAND                  CREATED          STATUS          PORTS                                NAMES
    04c9dd15a9b0   redis        "docker-entrypoint.s…"   2 seconds ago    Up 1 second     6379/tcp                             awesome_turing
    42fc88faccca   nginx        "/docker-entrypoint.…"   55 minutes ago   Up 55 minutes   80/tcp                               peaceful_feynman
    f6f175294090   nginx        "/docker-entrypoint.…"   5 hours ago      Up 5 hours      80/tcp                               intelligent_lewin
    45f6798e8c9b   httpd        "httpd-foreground"       7 hours ago      Up 7 hours      80/tcp                               pedantic_payne
    3dc0044aed4b   registry:2   "/entrypoint.sh /etc…"   16 months ago    Up 8 hours      5000/tcp, 0.0.0.0:32000->32000/tcp   registry
    ```

- second executed command:

    ```bash
    docker stop awesome_turing
    ```

- list all containers (`awesome_turing` container is removed):

    ```bash
    CONTAINER ID   IMAGE                                        COMMAND                  CREATED              STATUS                          PORTS                                NAMES
    8d229284248f   redis                                        "docker-entrypoint.s…"   About a minute ago   Exited (0) About a minute ago                                        elated_meitner
    6a8b347dde8e   ubuntu                                       "/bin/bash"              9 minutes ago        Exited (130) 2 minutes ago                                           my-container-5
    76799c447caf   ubuntu                                       "/bin/bash"              55 minutes ago       Exited (0) 55 minutes ago                                            nervous_agnesi
    141a7e6f3bae   nginx                                        "/docker-entrypoint.…"   56 minutes ago       Exited (0) 56 minutes ago                                            cool_hugle
    42fc88faccca   nginx                                        "/docker-entrypoint.…"   56 minutes ago       Up 56 minutes                   80/tcp                               peaceful_feynman
    5e9282a5e9bd   nginx                                        "/docker-entrypoint.…"   56 minutes ago       Exited (0) 56 minutes ago                                            agitated_kowalevski
    6daf42d14faf   ubuntu                                       "/bin/bash"              2 hours ago          Exited (0) 2 hours ago                                               amazing_noether
    18f44ce5003f   redis                                        "docker-entrypoint.s…"   2 hours ago          Exited (0) 9 minutes ago                                             my-container
    68e884e76ab9   caddy                                        "caddy run --config …"   2 hours ago          Exited (0) 2 hours ago                                               my-container-2
    d6465b3873ae   ubuntu                                       "/bin/bash"              2 hours ago          Exited (0) 2 hours ago                                               stoic_wing
    ```
