# Use in Docker Swarm Definition

- Docker Swarm doesn't natively support canary releases out of the box like Kubernetes (with tools like Istio);
- you can implement the canary pattern manually by deploying multiple service instances (e.g., `myapp-v1`, `myapp-v2`) and controlling traffic distribution using an external reverse proxy like Traefik.
