# Why not `docker compose up` Command Definition

**`docker compose up` command:**

* **local development tool**;
* brings up containers using the Docker Compose file **directly on the Docker Engine**;
* does **not use Docker Swarm mode**;


* it sets up containers, networks, and volumes, but **not as services managed by Swarm**.

**`docker stack deploy` command:**

* specifically, designed for **Swarm mode**;
* deploys a Docker Compose file as a **stack of services** onto the **Swarm cluster**;
* converts Docker Compose services into Swarm-managed services (with load balancing, replicas, constraints, etc.);


* requires the Docker Compose file to be in **version 3 or higher**, which is Swarm-compatible.
