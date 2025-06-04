# Run the Stack

- the `docker-compose.yml` file declare a `deploy` section with the number of replicas for backend service:

    ```commandline
    deploy:
      mode: replicated
      replicas: 2
    ```
- **this feature is available just using Docker Swarm, so we cannot run the stack with `docker compose up` command**.

**Step:**

Open a terminal within the project root folder and run this command:

```commandline
docker stack deploy -c docker-compose.yml complete-compose-example-stack
```
