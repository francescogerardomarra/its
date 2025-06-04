# Global Mode Definition

- it's a common practice to deploy Traefik as a **global** service (one per node);
- another, **less common**, approach is to deploy Traefik as a single service on just one node;
- in this setup, it functions as a centralized load balancer, routing traffic to services running across all nodes in the Swarm cluster;


- in the following table, we compare the two solutions.

**Table:**

| Mode                                  | Behavior                                       | Use case                                                                |
|---------------------------------------|------------------------------------------------|-------------------------------------------------------------------------|
| **Global**                            | one Traefik instance per Swarm node.           | best for high availability, local traffic routing, and host networking. |
| **Replicated (1 in all the cluster)** | one Traefik instance across the whole cluster. | ok for small setups, but a single point of failure.                     |
