# `host`

- docker compose driver `host` is a network driver that makes a container use the host's network stack directly;
- it means that the container does not get its own network namespace but shares the host's network interfaces;
- containers using the `host` driver can directly use the host’s IP address and port numbers, avoiding network address translation (NAT);
 

- because the container shares the host’s network, it does not have an isolated network interface, making it unsuitable for multi-container setups that require internal communication;
- `host` network mode is typically used for performance optimization when low latency or high throughput is critical, as it eliminates the overhead of virtual networking;
- it is only supported on **Linux hosts**, as Windows and Mac do not support host networking;
 

- port mapping specified in the `ports` section of a Docker Compose file is ignored when using the `host` driver;
- containers on different hosts cannot communicate with each other directly when using the `host` network mode;
- when using the host network driver in Docker, all containers running on the same host will share the same IP address as the host;


- there is no port mapping (like `-p 8080:80`) because the container does not get its own network namespace;
- the container binds directly to the host's network stack, so port `80` inside the container equals port `80` on the host;
- if you run two containers using the host driver and both try to bind to port `8080`, the second container will fail because the port is already occupied by the first one.

**Example:**

```yaml
version: '3.9'

services:
  app:
    image: nginx:latest
    container_name: my_nginx
    # ports: # You cannot use the host network driver in combination with ports in a Docker Compose file
      # - "8080:80" # Since the container uses the host’s network directly, you can access the service on: http://localhost:80
    networks:
      - my_bridge_network

networks:
  my_bridge_network:
    driver: bridge
```
