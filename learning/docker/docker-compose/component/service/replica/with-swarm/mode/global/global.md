# `global`

- `global` is a `deploy` option;
- it works just with Docker Swarm;
- runs one container per node in the Swarm;
 

- you can't set replicas in this mode.

**Example:**

```yaml
services:
  app:
    image: nginx:alpine
    deploy:
      mode: global
```
