# `replicated`

- `replicated` is a `deploy` option;
- it works just with Docker Swarm;
- it runs the service with the number of replicas you define (e.g., `replicas: 3`).

**Example:**

```yaml
services:
  app:
    image: nginx:alpine
    deploy:
      mode: replicated
      replicas: 3
```
