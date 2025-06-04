# Define Network Definition

- defining a network in Docker Compose means specifying a custom network that your services will connect to;
- networks in Docker Compose allow containers to communicate with each other, even if they are on different services;
- you can define multiple networks to isolate groups of services or control the communication between them;


- networks are declared under the `networks` key at the root level of the `docker-compose.yml` file;
- once defined, you can assign a network to a service using the `networks` key under each service configuration;
- networks can have additional configurations, like specifying a driver (e.g., `bridge`, `overlay`) or customizing IP address ranges;
 

- Docker Compose automatically creates a default network if no custom networks are specified;
- using custom networks improves security and flexibility by isolating services that don’t need to communicate directly.

**Example:**

```yaml
version: '3.9'

services:
  web:
    image: nginx:latest
    ports:
      - "8080:80"
    networks:
      - mynetwork

networks:
  mynetwork:
    driver: bridge
```
