# Connecting to Multiple Networks

In this chapter, we explain how a service can connect to multiple networks:

- a service in Docker Compose can connect to multiple networks by specifying those networks under the `networks` key within the service definition;
- each network must be defined separately under the `networks` section at the bottom of the `docker-compose.yml` file;
- when a container connects to multiple networks, it will have a separate network interface and IP address for each network;


- to make a service communicate with another service on a specific network, both services must be connected to that network;
- the primary network (default) for the service can be specified by defining `network_mode: bridge` or using `default` as the first network in the list;
- the order of networks in the `networks` list determines which network is the default for outbound traffic;


- **primary network**:
  - the primary network in Docker Compose is the network that the container uses by default when making connections **to services or addresses outside of Docker networks** (like the internet);
  - it also determines which network's **IP address** will be used as the container's main address when connecting to external systems.

**Example:**

```yaml
version: '3'
services:
  app:
    image: my-app-image
    network_mode: bridge  # Forces bridge network as primary ("backend")
    networks:
      - frontend
      - backend

  db:
    image: my-db-image
    networks:
      - backend
        
  web:
    image: my-web-image
    networks: # In this case primary network is "frontend"
      - frontend
      - backend

networks:
  frontend:
    driver: overlay
  backend:
    driver: bridge
```
