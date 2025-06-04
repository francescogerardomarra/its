# Replicated Definition

- a **replicated** service in Docker Swarm is a service where you specify how many identical container instances (replicas) you want running at the same time;
- Swarm will automatically distribute and maintain those replicas across the available nodes in your cluster.

**Example**

```commandline
docker service create \
  --name my_web \
  --replicas 3 \
  -p 8080:80 \
  nginx
```

- `--name my_web`: name of the service;
- `--replicas 3`: run 3 replicas of the service;
- `-p 8080:80`: publish port `80` from the container to port `8080` on the host;


- `nginx`: Docker image to run.
