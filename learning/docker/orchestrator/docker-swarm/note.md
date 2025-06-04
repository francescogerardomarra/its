**What is a Docker Swarm service:**

- a **Docker Swarm service** is a scalable and distributed application running on a **Docker Swarm cluster**;
- it allows you to define and manage containers across multiple nodes in a **swarm**;
- services can be of two types:
    - **replicated services**, where a specified number of container instances are distributed across the cluster;
    - **global services**, where one container runs on each node in the swarm;
- **Docker Swarm** ensures high availability and load balancing by distributing tasks among available worker nodes;
- when you create a service, **Docker Swarm** handles container scheduling, rolling updates, and self-healing automatically;
- you can create a service using the `docker service create` command, specifying the image, number of replicas, and other configurations;
- after deployment, you can manage the service using commands like `docker service ls`, `docker service ps`, and `docker service scale`.
