# Default Network

- when using Docker Compose, a default network is automatically created if no custom network is specified in the Compose file;
- the default network is typically a bridge network named using the format `<project_name>_default`, where `<project_name>` is derived from the name of the Compose file or the directory name;
- all services defined in the Docker Compose file are connected to this default network, allowing them to communicate with each other using the service names as DNS hostnames;
 

- the default network is isolated from other Docker networks, meaning containers outside the Compose project cannot access it unless explicitly allowed;
- you can override the default network by specifying a custom network configuration under the `networks` section of the Compose file.
