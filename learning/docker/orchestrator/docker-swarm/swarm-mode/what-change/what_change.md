# What Changes When Enabled

When you run `docker swarm init`:
- the local Docker engine becomes a Swarm manager node;
- if you want to join an existing Swarm (as a worker or manager), you should not run `init` but use the `docker swarm join` command (see [here](../../common-command/cluster/join/join.md));
- Docker creates a Swarm internal network (ingress) for load balancing services;


- you can now use `docker service` and `docker stack` commands;
- Docker creates a raft-based key-value store (Raft log) to store cluster state;
- nodes (local or remote) can join this Swarm using a token (`docker swarm join`);


- Docker sets up TLS certificates for secure communication between nodes.
