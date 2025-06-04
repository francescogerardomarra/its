# Volumes Definition

- volumes in Docker Swarm work similarly to volumes in regular Docker, but with some special considerations for the **clustered environment**;
- a volume:

  - is a storage mechanism managed by Docker;
  - can persist data outside the container's lifecycle;
  - can be shared between containers (with caveats in Swarm mode).
