# Swarm

- network scopes in Docker Compose define the accessibility and visibility of containers within and across Docker hosts;
- swarm scope, often represented by the `overlay` network driver, enables communication between containers running on different Docker hosts as part of a Docker Swarm cluster;
- `overlay` networks are created and managed by Docker Swarm, allowing multi-host communication across nodes in the swarm;


- services deployed in a Docker Compose file with "deploy" settings are automatically attached to `overlay` networks when using Swarm mode;
- containers on the same `overlay` network can communicate using service names as hostnames, regardless of the host they are running on.
