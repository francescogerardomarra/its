# Deploy the Docker Swarm service

- see what is a Docker Swarm service [here]; //TODO: link to orchestrator chapter
- enter the root [project](../maven-project/maven_project.md) folder with a terminal and run this command:

    ```commandline
    docker service create --name swarm-service --secret test-secret secret-swarm:1.0
    ```

**What the command does:**

- `docker service create`: creates a new service in a Docker Swarm environment;
- `--name swarm-service`: specifies the name of the service as `swarm-service`;
- `--secret test-secret`: 
  - mounts a secret named `test-secret` into the service, which must be previously created using [docker secret create](../create-secret/definition/definition.md);
  - the secret will be automatically mounted inside the container at `/run/secrets/test-secret`;
  - you can see within the [project code](../maven-project/maven_project.md) that `SecretManager` class read it from that location.
- `secret-swarm:1.0`: defines the Docker image to be used for the service, specifically [secret-swarm:1.0](../build-image/build_image.md).
