# `docker-compose.yml` Explanation

Explanation with comments:

```yaml
version: "3.8"  # Specifies the Docker Compose file format version

services:
  swarm-service:  # Defines a service named 'swarm-service'
    image: secret-swarm:1.0  # Uses the Docker image 'secret-swarm' with tag '1.0'
    deploy:
      mode: replicated  # Ensures the service runs in replicated mode in Docker Swarm
      replicas: 1  # Specifies the number of container instances to run

    secrets:
      - test-secret  # Specifies the secret 'test-secret' to be used by this service

secrets:
  test-secret:  # Defines a secret named 'test-secret'
    external: true  # Indicates that this secret is managed externally in Docker Swarm
```
