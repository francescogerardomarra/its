# `ipv4_address`

- the `ipv4_address` option in Docker Compose is used to assign a static IP address to a container within a user-defined network;
- it is specified under the `networks` section of an individual service within the `docker-compose.yml` file;
- to use `ipv4_address`, the network **must** be configured with IPAM (IP Address Management), specifying a subnet;


- the `ipv4_address` option will not work without IPAM configuration;
- the static IP must fall within the range defined by the subnet to avoid conflicts;
- the assigned IP address ensures that the container always gets the same IP, which is useful for inter-container communication;


- if the `ipv4_address` is not specified, Docker will dynamically allocate an IP from the available subnet range;
- this option is commonly used with custom bridge networks and overlay networks in Docker Compose;
- the `ipv4_address` does not affect how the container is accessed from the host machine, as host access is managed through port mapping.

**Example:**

```yaml
version: '3.9'

services:
  web:
    image: nginx:latest
    container_name: web_server
    networks:
      my_network:
        ipv4_address: 192.168.50.10
    ports:
      - "8080:80"

  db:
    image: postgres:latest
    container_name: db_server
    environment:
      POSTGRES_PASSWORD: secret
    networks:
      my_network:
        ipv4_address: 192.168.50.20

networks:
  my_network:
    driver: bridge
    ipam:
      config:
        - subnet: 192.168.50.0/24
          gateway: 192.168.50.1
```
