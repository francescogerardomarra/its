# Environment Variables

- `DOCKER_HOST`: configures Docker to communicate over a TCP socket to [`docker:dind` service](../docker-service/docker_service.md);
- `DOCKER_TLS_CERTDIR`: disables TLS certificate generation to simplify Docker-in-Docker communication.

**`gitlab-ci.yml` Section:**

```yaml
variables:
  DOCKER_HOST: tcp://docker:2375
  DOCKER_TLS_CERTDIR: ""
```
