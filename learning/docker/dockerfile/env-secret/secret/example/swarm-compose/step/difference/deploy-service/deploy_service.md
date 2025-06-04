# Deploy the Docker Swarm Service

- see what is a Docker Swarm service [here]; //TODO: link to orchestrator chapter
- enter the root [project](../maven-project/download/download.md) folder with a terminal and run this command:

    ```commandline
    docker stack deploy -c docker-compose.yml my_stack
    ```

**What the command does:**
- this deploys all services defined in `docker-compose.yml`.
- they run as a **Swarm-managed stack**;
- see what is a Swarm stack [here](../../../../../../../../orchestrator/docker-swarm/index.md).
