# Docker Compose Definition

Docker Compose is not an orchestrator; it is a tool for defining and running multi-container applications, but it **does not** provide orchestration capabilities like Swarm or Kubernetes:

- Docker Compose doesn't manage the secret itself, it needs an orchestrator, like Docker Swarm to manage secrets;
- it's common to integrate Docker Compose within Docker Swarm;
- integrating Docker Swarm with Docker Compose simplifies deployment and management of multi-container applications across a Swarm cluster;
- instead of manually creating and managing individual Swarm services, you can define everything in a `docker-compose.yml` file and deploy it with one command.
