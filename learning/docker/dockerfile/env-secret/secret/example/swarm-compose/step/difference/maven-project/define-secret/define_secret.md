# Define Secret within `docker-compose.yml` File

- instead of creating a secret with secret create command, it's possible to save it in a file locally and load it to the Docker Swarm service;
- this strategy is less recommended since the secret is **not** encrypted at rest before being added to Swarm, increasing the risk of exposure:

    ```yaml
    version: "3.8"  # Specifies the Docker Compose file format version
    
    services:
      swarm-service:  # Defines a service named 'swarm-service'
        image: secret-swarm:1.0  # Uses the Docker image 'secret-swarm' with tag '1.0'
        deploy:
          mode: replicated  # Ensures the service runs in replicated mode in Docker Swarm
          replicas: 1  # Specifies the number of container instances to run
    
        secrets:
          - test-secret  # Specifies the secret 'test-secret' to be used by this service
    
    secrets:
      test-secret:  # Defines a secret named 'test-secret'
        file: ./test-secret.txt  # Loads the secret from a local file named 'test-secret.txt'
    ```
