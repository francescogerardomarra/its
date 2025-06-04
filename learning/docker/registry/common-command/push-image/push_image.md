# Push-Pull Image To Registry

- **purpose**: 
  - the `docker push` command uploads a locally tagged Docker image to a remote registry;
  - this makes the image available for use by others or for deployment in various environments.
- **syntax**: the command format is:

    ```bash
    docker push <registry>/<username>/<repo-name>:<tag>
    ```  
  - `<registry>` specifies the target Docker registry (e.g., `registry.example.com`), default registry is Docker Hub if not specified;
  - `<username>/<repo-name>:<tag>` indicates the username, repository and tag within the registry.
- **example**: to push a tagged image `registry.example.com/avolpe98/my-app:latest` to the `registry.example.com` under `my-app` repository, run:

    ```bash
    docker push registry.example.com/avolpe98/my-app:latest
    ```  
    
    this uploads the image to the specified registry for future access.
