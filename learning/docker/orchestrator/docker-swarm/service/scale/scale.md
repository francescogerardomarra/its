# Scale Service

- when you see that the running service needs more or less replicas, you can scale it by updating the service;
- run this command on a manager node:

    ```commandline
    docker service update --replicas 10 my_service
    ```

- or you can use another command:

    ```commandline
    docker service scale my_service=10 my_service
    ```

    - **this command calls the `docker service update` under the hood**.

**Using Docker Compose:**

If you're using a service deployed with Docker Compose that has a specific number of replicas defined in the `docker-compose.yml` file, and you manually scale the service using `docker service update --replicas`: <!-- todo: link to the ovveridding manually replicas in the Docker Compose chapter -->
- it overrides the number of replicas defined in your `docker-compose.yml` file, but only at runtime, not in the file itself;
- the number of replicas will revert to what's defined in the `docker-compose.yml` only when you redeploy the stack using:

    ```commandline
    docker stack deploy -c docker-compose.yml mystack
    ```
