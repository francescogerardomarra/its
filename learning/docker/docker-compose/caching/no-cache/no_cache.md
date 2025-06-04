# Forcing or Avoiding Cache

- in the context of Docker and Docker Compose, the `--no-cache` option is used when building images to tell Docker not to use any cached layers from previous builds;
- Docker will rebuild the image from scratch;
- it will not reuse any intermediate layers from previous builds;


- every step in your Dockerfile will be executed fresh, even if nothing has changed.

**Example:**

```commandline
docker-compose build --no-cache
```
