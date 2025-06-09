# Stages

Defines two pipeline stages:

1. `maven-build`: for building the Java project;
2. `image-build-and-push`: for building and pushing the Docker image.

**`gitlab-ci.yml` Section:**

```yaml
stages:
  - maven-build
  - image-build-and-push
```
