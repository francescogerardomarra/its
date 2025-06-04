# Introduction

- when you deploy a service in Swarm mode, you can specify how many **replicas** (copies) of the service you want;
- Docker Swarm uses **built-in DNS-based load balancing** and **routing mesh**.

**Routing mesh**: 

- all nodes in the swarm can receive traffic on a service's published port and route it to a container running that service, regardless of which node it's actually on;
- example:
  - service is published on port `80`;
  - any node in the swarm accepts traffic on `port 80`;
  - Swarm routes the request to one of the service replicas.

**DNS-based load balancing**: 

- Swarm has an internal DNS system that resolves a service name to a list of container IPs;
- when a container tries to access `web`, Swarm’s DNS resolves it to one of the replicas’ IPs.
