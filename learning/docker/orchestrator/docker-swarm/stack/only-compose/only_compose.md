# Exist Just with Docker Compose

- **stacks** are a concept that exists only when using Docker Swarm in conjunction with Docker Compose files;
- you can deploy individual services using [docker service create](../../common-command/service/create/create.md), but there is no concept of a "stack" in this mode;
- stacks are a higher-level abstraction that groups multiple services, allowing them to be managed as a unit via Docker Compose in Swarm mode.
