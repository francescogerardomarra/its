# `bridge`

- docker compose driver "bridge" is the default network driver for containers on a single host;
- it creates a private internal network on the host machine where containers can communicate with each other;
- each container gets an IP address from the bridge network, allowing them to interact via IP or container name;
 

- containers connected to the same bridge network can communicate directly, while those on different networks cannot unless explicitly connected;
- host machine can access the containers through port mapping, allowing external services to communicate with the containers;
- bridge networks are ideal for application stacks where multiple services need to communicate within the same host;
 

- bridge networks can be customized with specific IP ranges and subnet configurations to avoid conflicts.

**Example:**

```yaml
version: '3.9'

services:
  app:
    image: nginx:latest
    container_name: my_nginx
    ports:
      - "8080:80"
    networks:
      - my_bridge_network

networks:
  my_bridge_network:
    driver: bridge
```
