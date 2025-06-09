# Set Up Local Registry

- **purpose**: a private [Docker registry](../../self-hosting/example/run/run.md) allows you to host and manage your own repository for Docker images locally or within your organization, avoiding reliance on external registries like Docker Hub;
- **command overview**: the basic command to set up a local registry is:

    ```bash
    docker run -d -p 5000:5000 --name registry registry:2
    ```  
    - it runs a Docker container named `registry` in detached mode (`-d`), exposes it on port `5000`, and uses the official Docker `registry` image (version `2`).
- **optional volume storage**: to make the registry's data persistent, add a [volume](../../../volume/index.md):

    ```bash
    docker run -d -p 5000:5000 -v /path/to/registry/data:/var/lib/registry --name registry registry:2
    ``` 
    
    this maps a local directory (`/path/to/registry/data`) to the registry's data directory inside the container, ensuring images remain stored even if the container is restarted or removed.
