# How It Works

When you deploy a service in Swarm:
- a virtual IP (VIP) is created for the service;
- **all nodes** in the Swarm **listen** to the published port of that service;
- Docker uses iptables and load-balancing rules to forward requests that arrive at any node to an available container for the service, this is the mesh;
 

- so even if a service container is running only on `node A`, and a request comes to `node B`, Docker will route the traffic internally to `node A`;
- if the service has more replicas, an internal load balancer of the routing mesh routes the request to one of them using a round-robin policy.
