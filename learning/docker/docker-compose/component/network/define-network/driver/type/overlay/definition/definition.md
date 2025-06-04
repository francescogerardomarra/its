# `overlay` Definition

- docker compose driver `overlay` is used to connect containers running on different Docker hosts, forming a single virtual network across multiple machines;
- it enables communication between containers running on separate Docker nodes within a Docker Swarm or Kubernetes cluster;
- the primary use case of overlay networks is to support multi-host container communication, essential for distributed and scalable applications;


- the network traffic between containers is encrypted by default, ensuring secure communication;
- overlay networks are managed by Docker’s built-in network driver and require swarm mode to be enabled (`docker swarm init`);
- it works by encapsulating network traffic using VXLAN (Virtual Extensible LAN) to enable communication between hosts;


- each service running in an overlay network can resolve other services using built-in DNS, allowing containers to communicate by service name rather than IP address;
- when a new container joins an overlay network, it automatically gets an IP address from the network's IP range;
- load balancing between containers is performed automatically, distributing incoming requests across replicas within the same service;


- overlay networks are useful for deploying microservices, load-balanced applications, and distributed databases within a swarm cluster.
