# Why Is Distributed (Advantages)

- **high availability:**
  - every node can accept requests on the published port;
  - if a node or service fails, other nodes can continue serving requests;
  - this removes single points of failure in ingress routing (incoming network traffic from outside the swarm cluster).
- **scalability:**
  - you can scale services horizontally across nodes;
  - incoming requests are automatically distributed across the different containers running on various nodes in the cluster, using a built-in Linux component called IPVS (IP Virtual Server), which handles load balancing behind the scenes.
- **simplified networking:**
  - developers and system administrators don’t have to track which machine is running which service, Docker Swarm handles that automatically;
  - any node in the cluster can accept incoming traffic, and Docker will route it behind the scenes to the correct container, wherever it's running in the swarm.


- **uniform access:**
  - with the routing mesh, the **published port is available on all nodes**, not just the ones where the container runs;
  - this gives a **uniform network interface** to external clients.
- **internal load balancing**
  - Docker Swarm uses **IPVS**, a layer-4 load balancer built into the Linux kernel;
  - it efficiently distributes traffic to service replicas on different nodes.
