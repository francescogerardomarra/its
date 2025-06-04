# Use Docker Swarm

Run this Docker Swarm-specific command to see the logs:

```commandline
docker service logs my_stack_swarm-service
```

**output:**

```commandline
my_stack_swarm-service.1.vd6ib1qi9tne@avolpe-pc    | [SecretManager] secret: SuperSecurePassword12345
my_stack_swarm-service.1.vd6ib1qi9tne@avolpe-pc    | [Main] secret: SuperSecurePassword12345
```
