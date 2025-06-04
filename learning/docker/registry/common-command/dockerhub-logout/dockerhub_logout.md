# Docker Hub Logout

- **purpose**: 
  - the `docker logout` command is used to log out of a Docker registry, removing your authentication credentials for that registry from the Docker client;
  - this means the stored credentials (username and password or access tokens) for that specific registry are deleted from Docker's configuration files, preventing further automated access to the registry without re-authentication;
  - once you log in, Docker automatically uses these stored credentials for operations like `docker push` and `docker pull`, so logging out ensures these actions are no longer possible without re-entering your credentials.
- **syntax**: 
  - you specify the registry URL as an argument to the command, like `docker logout <registry-url>`;
  - if no registry URL is provided, it defaults to logging out of Docker Hub.
- **example**: to log out from a specific registry, such as `registry.example.com`, use:

    ```bash
    docker logout registry.example.com
    ```  
  
    this ensures your credentials for that registry are securely removed.