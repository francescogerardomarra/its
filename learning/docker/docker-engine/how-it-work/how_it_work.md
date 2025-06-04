# How It Works

- **containerization**:
    - Docker Engine packages applications and their dependencies into standardized units called containers;
    - containers run on top of the host system using shared resources without requiring a full guest OS, making them lightweight and efficient.
- **image management**:
    - Docker Engine uses images as the blueprint for containers;
    - developers can build images using a `Dockerfile` or pull pre-built images from Docker Hub or private registries;
    - the daemon manages the lifecycle of images, including pulling, caching, and tagging.
- **resource management**:
    - Docker Engine efficiently manages system resources (CPU, memory, disk) to ensure containers run smoothly;
    - it supports isolation mechanisms (via namespaces and cgroups) to prevent resource conflicts between containers.


- **networking**:
    - Docker Engine provides tools to manage networking between containers and external systems;
    - it supports various network modes like bridge, host, none, and custom networks.
