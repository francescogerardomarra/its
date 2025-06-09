# Built Images

- when Docker Compose has to build an image for a service, the [Dockerfile caching rules](../../../../dockerfile/index.md) are preserved;
- the `docker compose build` command will used the [Dockerfile cached layers](../../../../dockerfile/caching/example/optimized/optimized.md).
