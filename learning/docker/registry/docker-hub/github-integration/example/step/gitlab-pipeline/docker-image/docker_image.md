# Docker Image Build and Push Job

- runs in the `image-build-and-push` stage;
- builds a Docker image with a tag based on the commit SHA and `latest`;
- logs in to Docker Hub using `$DOCKER_USERNAME` and `$DOCKER_PASSWORD` variables;


- pushes both the commit-specific and `latest` tags to Docker Hub;
- depends on the `maven-build-step-one` step to access the built `.jar` artifact (the artifact is copied by the [Dockerfile](../../dockerfile/dockerfile.md));
- executes only on the `master` branch.

**`gitlab-ci.yml` Section:**

```yaml
image-build-and-push-step-one:
  stage: image-build-and-push
  script:
    - docker build -t $DOCKER_USERNAME/docker-hub-test:$CI_COMMIT_SHORT_SHA .
    - docker tag $DOCKER_USERNAME/docker-hub-test:$CI_COMMIT_SHORT_SHA $DOCKER_USERNAME/docker-hub-test:latest
    - echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
    - docker push $DOCKER_USERNAME/docker-hub-test:$CI_COMMIT_SHORT_SHA
    - docker push $DOCKER_USERNAME/docker-hub-test:latest
  dependencies:
    - maven-build-step-one
  only:
    - master
```
