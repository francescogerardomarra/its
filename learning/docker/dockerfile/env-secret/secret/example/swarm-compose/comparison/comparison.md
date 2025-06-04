# Comparison

**Before the possibility of deploying Swarm services managed with Docker Compose was introduced, managing **multi-service applications** in Swarm required:**
- manually creating each service using `docker service create`;
- manually setting up **networking**, **volumes**, and **secrets**;
- manually linking services together (database, backend, frontend, etc.).

**By integrating Docker Compose with Swarm, Docker made it possible to:**
- define **all services, networking, volumes, and secrets** in **one file** (`docker-compose.yml`);
- deploy everything in **one command** (`docker stack deploy`);
- automatically **set up networking and service discovery** within the stack.
