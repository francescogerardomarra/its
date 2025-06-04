# Tag Image for Registry

- **purpose**: 
  - the `docker tag` command assigns a new tag, to an existing Docker image;
  - this is necessary to prepare the image for pushing to a Docker registry.
- **syntax**: the command follows this format:

    ```bash
    docker tag <image>:<tag> <registry>/<username>/<repo-name>:<tag>
    ```  
    - `<image>:<tag>` is the local image name and tag (e.g., `my-app:latest`);
    - `<registry>/<username>/<repo-name>:<tag>` specifies the tag added for pushing the image to the registry;
    - if `<registry>` is omitted, the default Docker Hub registry will be used.
- **example**: to tag a local image `my-app:latest` for pushing to the `registry.example.com` under the repository `my-app`, you would run:

    ```bash
    docker tag my-app:latest registry.example.com/avolpe98/my-app:latest
    ```  
    
    this creates a new tag that references to the same image, but the new tag includes the registry details.
