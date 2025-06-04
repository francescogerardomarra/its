# Example

- suppose you have a service running 6 replicas:

    ```bash
    docker service update \
      --image myapp:v2 \
      --update-parallelism 2 \
      --update-delay 10s \
      my_service
    ```
- with the above [docker service update]() command, you are performing a rolling update of the Docker Swarm service `my_service` to use the new image `myapp:v2`; <!-- todo: link to common commands -> docker service update command --> 
- Swarm will:
    1. shut down 2 old replicas and start 2 new ones using `myapp:v2`;
    2. wait 10 seconds;
    3. if the new replicas are healthy, proceed to update the next 2;
    4. repeat until all 6 replicas are updated.


- during all update operation, at least 4 containers are running, **ensuring no downtime**.
