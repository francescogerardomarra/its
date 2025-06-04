# Workflow

- **development**:
  - developers write Dockerfiles to define application environments;
  - build an image using `docker build`.
- **image storage**:
  - push the image to a registry for sharing (`docker push`);
  - pull the image from the registry as needed (`docker pull`).
- **container execution**:
  - run containers using `docker run`;
  - manage containers with commands like `docker ps`, `docker stop`, etc.
