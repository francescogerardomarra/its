# Pull Image From Registry

- **purpose**: 
  - the `docker pull` command is used to download an image from a remote Docker registry to your local machine;
  - this allows you to use the image for running containers or further development.
- **syntax**: the command format is:

    ```bash
    docker pull <registry>/<username>/<repo-name>:<tag>
    ```  
  - `<registry>` specifies the registry to pull the image from (e.g., `registry.example.com`), default registry is Docker Hub if not specified;
  - `<username>/<repo-name>:<tag>` indicates the username, repository and tag within the registry.
- **example**: to pull a tagged image `registry.example.com/avolpe98/my-app:latest` from the `registry.example.com` under `my-app` repository, run:

    ```bash
    docker pull registry.example.com/avolpe98/my-app:latest
    ```  
  
    this fetches the specified image to your local Docker environment for use.
