# Example `docker-compose.yml` File

- in this example, we defined three services, `web`, `app`, and `db`, that communicate over two overlay networks (`frontend` and `backend`);
- enabling secure inter-service communication in a Docker Swarm environment;
- `driver: overlay` creates an overlay network;


- containers in the same network (`frontend`, `backend`) can communicate with each other using service names;
- this only works in **Swarm mode** (`docker stack deploy`), not with `docker-compose up`;
- in this example, we used the overlay network because it's required when using Docker Swarm, as services can potentially be distributed across different nodes and need to communicate over a virtual network;


- even on a single-node Swarm, overlay is mandatory for service-to-service communication when deploying with `docker stack deploy`, because Docker Swarm is designed to support scaling across multiple nodes seamlessly.

**`docker-compose.yml` file:**

```yaml
version: '3.8'

services:
  web:
    image: nginx
    networks:
      - frontend

  app:
    image: myapp
    networks:
      - frontend
      - backend

  db:
    image: postgres
    networks:
      - backend

networks:
  frontend:
    driver: overlay
  backend:
    driver: overlay
```
