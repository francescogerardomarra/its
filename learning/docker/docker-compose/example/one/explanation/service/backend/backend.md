# Backend

- backend has a pre-build image (you have to [manually build it](../../../run/build-backend/build_backend.md) before running the `docker-compose.yml` file);
- the image builds a Spring Boot application managed with Maven, generating a `.jar` file;
- the image set to run the `.jar` file when a container will be instantiated from it.
