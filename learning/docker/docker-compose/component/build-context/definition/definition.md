# Build Context Definition

- in Docker Compose, **if a service needs to build an image** (i.e., a `build:` section is defined), Docker Compose will use the specified [build context](../../../../dockerfile/build-context/build_context.md) for that service, which is the directory sent to the Docker daemon for building;
- if there are **multiple services being built**, **each service can (and usually does) have its own build context**;
- a common project structure is to have **one folder per service**, and each folder contains:
    - a `Dockerfile`;
    - other files needed during build;
    - this folder becomes the **build context** for that service.


- the build context is defined in the `docker-compose.yml` file using the `context:` key, under the `build:` section.
