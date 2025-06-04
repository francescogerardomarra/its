# Interactively Run VS Execute

- the command that runs when the container starts are defined in the image, typically under the `CMD` or `ENTRYPOINT` directives in the image's Dockerfile;
- in an Ubuntu image, the `CMD` is usually `/bin/bash`;
- in other images, it could be something else (e.g., `nginx -g 'daemon off;'` for an NGINX image).


**Ubuntu Image (default is `/bin/bash`):**

- run the container in interactive mode:

    ```bash
    docker run -it ubuntu
    ```

- the container starts, and since the default command in the Ubuntu image is `/bin/bash`, you are dropped into a bash shell.

**Nginx Image:**

- run the container in interactive mode:

    ```bash
    docker run -it nginx
    ```

- the nginx image does not have `/bin/bash` as its default command;
- instead, it runs nginx as the default command;
- you would not get a shell unless you explicitly override it.

**Override default command:**

This overrides the default command and gives you a bash shell (if bash is available in the image):

```bash
docker run -it nginx /bin/bash
```

**Interactively `run` and `execute` Comparison:**

- use `docker run -it` to attach to and execute commands in a new container;
- use `docker exec -it` to attach to and execute commands in an existing container.
