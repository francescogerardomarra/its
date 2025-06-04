# Relation with Service

When you run a service in Swarm mode (e.g., `docker service create --replicas 3 nginx`), Docker Swarm does this:
1. creates a service definition;
2. decides which nodes in the Swarm will run containers;
3. creates tasks for each container;
4. assigns and schedules these tasks on nodes.

**Example:**

```commandline
docker service create --replicas 3 nginx
```

- this tells Docker Swarm:
  - you want a service using the nginx image;
  - you want 3 replicas;
  - Docker Swarm creates 3 tasks, each of which results in a running container on available nodes.
