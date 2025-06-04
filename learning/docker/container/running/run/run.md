# Run the Container

Once the image is pulled, run it
using the `docker run` command:
```bash
docker run <options> <image_name>
```

Here are some common options you might use:

- **`-d`**: Run the container in detached mode (in the background).
- **`--name <container_name>`**: Give your container a specific name.
- **`-p <host_port>:<container_port>`**: Map a port on your host to a port in the container.
- **`-v <host_path>:<container_path>`**: Mount a volume (optional).
- **`-e <ENV_VAR>=<value>`**: Set environment variables in the container.

Example:
```bash
docker run -d --name mynginx -p 8080:80 nginx
```

- this example runs a Nginx container, mapping port 80 in the container to port `8080` on the host;
- the `-d` flag runs it in the background.
