# Maven Project

- download [secret-kubernetes.zip](resources/secret-kubernetes.zip) Maven project;
- this project contains:
    - Java classes:
        - `SecretManager`: contains a `getSecret` method to retrieve the secret from Docker Swarm;
        - `Main`:
            - contains the `main` method that retrieved the secret using `getSecret` and keeps sleeping to avoid the Docker Swarm service to stop or restart;
            - since the container within the service stops if it has no running processes.
        - Dockerfile;
        - `deployment.yaml` file.
