# Enable Docker Swarm

- **initialize Swarm mode**: this command turns the current Docker host into a Swarm manager, enabling Docker Swarm mode for orchestrating containers across multiple nodes;
- **creates a Swarm cluster**: if no existing Swarm is available, this initializes a new Swarm cluster with the current node as the first manager;
- **generates join tokens**: after execution, it provides tokens that allow other nodes (workers or managers) to join the Swarm;


- **configures networking & security**: it sets up an overlay network for container communication and secures the cluster with TLS encryption;
- **Swarm mode stays active**: 
  - once you run `docker swarm init`, the Docker daemon on that node remains in Swarm mode unless you explicitly leave or disable it using `docker swarm leave --force` (for managers node);
  - this means Docker will continue to operate within the Swarm orchestration environment.
- **regular Docker still works**: 
  - even with Swarm mode enabled, you can still run regular Docker commands (like `docker run` or `docker compose`);
  - however, if you use `docker service` commands, they will be managed by Swarm instead of standalone containers.

**Command to Initialize Swarm Mode:**

```commandline
docker swarm init
```
