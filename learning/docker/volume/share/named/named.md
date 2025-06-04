# Named Volumes

- you can share a named volume between multiple containers:

    ```commandline
    docker run --name container-1 -v my-volume:/app/data my-image-1
    docker run --name container-2 -v my-volume:/tmp/my-folder my-image-2
    ```

- **all concurrency problems can happen when multiple containers share the same volume and access the same files without proper coordination**;
- see the bind mount example [here](../bind/bind.md).
