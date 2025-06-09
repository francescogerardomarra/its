# Docker Service

- enables Docker-in-Docker (`docker:dind`) service for container management during the pipeline;
- the **`docker:20.10.24` image** provides only the Docker CLI, which allows you to execute Docker commands;
- the **`docker:dind` service** (Docker-in-Docker) runs a separate container that contains the Docker daemon;


- this allows the pipeline to execute Docker commands (like `docker build` and `docker push`) by communicating with the daemon over the network, typically via `tcp://docker:2375`.

**`gitlab-ci.yml` Section:**

```yaml
services:
  - docker:dind
```
