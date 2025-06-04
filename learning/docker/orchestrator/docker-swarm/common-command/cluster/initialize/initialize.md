# Initialize a Swarm

**Command:**

```commandline
docker swarm init
```

**Command explanation:**

- initializes a new Docker Swarm mode on the current machine, making it the manager node;
- sets up the internal network and Raft consensus system required for managing services in a swarm;
- generates a unique join token used by other nodes (workers or managers) to join the swarm;


- configures the local Docker engine to operate in swarm mode and manage services across the cluster;
- typically used as the first step when setting up a swarm-based container orchestration system.
