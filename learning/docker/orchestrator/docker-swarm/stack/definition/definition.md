# Stack Definition

- in **Docker Swarm**, a **stack** is a **collection of services** that are deployed and managed together;
- it is essentially a higher-level abstraction used to **define and manage multi-service applications** using a single configuration file;
- a **stack** groups together **multiple services**, **networks**, and **volumes** as defined in a **Docker Compose file** (`docker-compose.yml`);


- it is designed to be **deployed as a unit** in a Docker Swarm cluster;
- stacks are only available when **Swarm mode** is enabled (`docker swarm init`).
