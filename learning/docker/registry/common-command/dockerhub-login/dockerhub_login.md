# Docker Hub Login

- **purpose**: the `docker login` command is used to authenticate your Docker client with a Docker registry, allowing you to push or pull container images securely;
- **syntax**: 
  - you provide the registry URL as an argument to the command;
  - for example: `docker login <registry-url>`;
  - if no registry URL is specified, it defaults to Docker Hub.
- **example**: to authenticate with a specific registry, such as `registry.example.com`, you would use:

    ```bash
    docker login registry.example.com
    ```  

    the command will prompt you for a username and password to complete the authentication.
