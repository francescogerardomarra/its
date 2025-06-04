# Service Network Attachment

In this chapter, we explain how to connect multiple services to the same network:

- define a network under the `networks` section of the Docker Compose file using a unique name, specifying any desired settings like driver type;
- assign the same network to multiple services by referencing the network name under each service's `networks` key;
- ensure that all services intended to communicate over the same network are explicitly linked to that network;
 

- when multiple services are connected to the same network in Docker Compose, they can communicate using their service names as hostnames;
- Docker's built-in DNS resolution allows you to refer to another service directly by its name, rather than using IP addresses;
- manage network isolation by creating multiple networks if needed, assigning services to the relevant networks;


- Docker Compose automatically sets up an internal DNS server that resolves the service names to their corresponding container IPs.

**Example:**

```yaml
version: '3.9'

services:
  app:
    image: myapp:latest
    container_name: app_service
    networks:
      - sharednetwork

  db:
    image: mysql:5.7
    container_name: db_service
    environment:
      MYSQL_ROOT_PASSWORD: example
      MYSQL_DATABASE: mydb
    networks:
      - sharednetwork
  
networks:
  sharednetwork:
    driver: bridge
```
