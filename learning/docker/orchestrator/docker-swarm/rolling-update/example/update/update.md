# Example

- in this example, we want to update the image version (from `myapp:v1` to `myapp:v2`) of a service with 5 replicas:

    ```bash
    docker service update \
      --image myapp:v2 \
      --update-parallelism 2 \
      --update-delay 10s \
      my_service
    ```

- this will:
  1. **first batch (2 tasks updated)**:
     1. Swarm selects **2 tasks** to update first;
     2. these 2 containers are deregistered from the load balancer, they no longer receive traffic;
     3. new containers are started using `myapp:v2` to replace them;
     4. these new containers must **reach a running state** (**as soon as each task is in the "running" state, Swarm considers it successful regardless of healthcheck**);
     5. once each container is confirmed running, the new task is added to the service's [routing mesh](); <!-- todo: link to load balancer chapter -->
     6. the 2 old containers are **shut down** gracefully;
     7. only after the new containers are running successfully does the process continue;
  2. **delay**: <!-- todo: check if this match with the delay already explained in previous chapters -->
     - Swarm **waits 10 seconds** before proceeding to the next batch;
     - this delay allows time for monitoring, rollback if necessary, or manual intervention;
  3. **second batch of updates**:
     - the next **2 containers** will be replaced;
     - all points from 1 (**first batch**) to 2 (**delay**) are repeated;
  4. **final update**
     - the last **container** will be replaced;
     - all points from 1 (**first batch**) to 2 (**delay**) are repeated, **but just the last remaining container is selected to be replaced**.
