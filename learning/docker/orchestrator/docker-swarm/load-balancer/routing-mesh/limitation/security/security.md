# Security and Isolation

- in Docker Swarm, when you publish a service port using the default **routing mesh** (`--publish mode=ingress`), **all nodes in the cluster will listen on that port**, even if the actual service container is **not running on those nodes**.
- this means any node, even one that doesn’t run the service, can accept incoming traffic and forward it to the appropriate container elsewhere in the cluster;
- in a **multi-tenant environment** (where multiple users, teams, or customers share the same Swarm cluster), this behavior can pose a **security risk**:
    - a user with access to a node could potentially see or interfere with traffic for services they don’t own;
    - it increases the **attack surface**, since every published port is open on every node.


- to reduce these risks:
  - use **host mode publishing** (`--publish mode=host`) to ensure only nodes running the service listen on the port;
  - avoid publishing sensitive services directly to the swarm ingress network;
  - consider separating tenants across different Swarm clusters or using strict network/firewall rules.
