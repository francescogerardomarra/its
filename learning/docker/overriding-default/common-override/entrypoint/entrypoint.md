# Entrypoint

You can override the default entrypoint using this command:

```commandline
docker run --entrypoint <new_entrypoint> <image_name> [arguments...]
```

**Example:**

- let's say the Docker image `ubuntu:latest` has an entrypoint that runs `/bin/bash` by default, but you want to override it to run `/bin/echo` instead:

    ```commandline
    docker run --entrypoint /bin/echo ubuntu:latest "Hello, World!"
    ```

- `--entrypoint /bin/echo` overrides the entrypoint;
- `ubuntu:latest` is the image;


- `"Hello, World!"` is the argument passed to the entrypoint (see [here](../../../dockerfile/common-command/entrypoint/entrypoint.md)).
