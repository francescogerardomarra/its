# CLI

- you can define volumes in your service using the `--mount` flag:

    ```bash
    docker service create \
      --name myservice \
      --mount type=volume,source=myvolume,target=/data \
      nginx
    ```

- it creates a new Docker Swarm service named `myservice`;
- it uses the `nginx` image as the service's container;
 

- it mounts a Docker volume named `myvolume` to the container's `/data` directory;
- it sets the mount type to `volume`, which tells Docker to use a named volume (`myvolume`) for storing data; 
- this ensures that data in the `/data` directory persists across container restarts or replacements, unlike bind mounts (which link to host file paths) or temporary mounts (which are deleted when the container stops);


- the `--mount` flag is mandatory, since `-v` (see [here]()) works only with docker run, not with `docker service create`. <!-- todo: link to -v common command is volumes chapter -->
