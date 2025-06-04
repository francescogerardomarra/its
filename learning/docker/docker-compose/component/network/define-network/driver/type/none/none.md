# `none`

- docker compose driver `none` is a network driver that completely disables networking for a container;
- containers using the `none` driver do not have any network interfaces, including loopback interfaces;
- the container is isolated from the host network and cannot communicate with other containers or the external world;


- typically used for highly isolated workloads or when networking is explicitly not required;
- useful in scenarios where network communication poses a security risk or needs to be intentionally blocked;
- because no network interface is created, you cannot map ports or perform any network operations;


- containers with the `none` driver can still access the filesystem, execute commands, and perform internal processing.

**Example:**

```yaml
version: '3.9'

services:
  isolated_app:
    image: busybox # BusyBox is a lightweight Linux image with basic UNIX utilities for quick testing and minimal containers.
    container_name: isolated_container
    command: sleep 3600 # the command `sleep 3600` makes the container **pause for 3600 seconds (1 hour)**, keeping it running without doing anything else,
                        # if the container does not have a long-running process, it will immediately stop after starting.
    networks:
      - no_network

networks:
  no_network:
    driver: none
```
