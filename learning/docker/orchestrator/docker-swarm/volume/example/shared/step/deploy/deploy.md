# Deploy to Docker Swarm

1. make sure you're in **Swarm mode**:

    ```bash
    docker swarm init  # if not already done
    ```

2. then deploy:

    ```bash
    docker stack deploy -c docker-compose.yml mystack
    ```
