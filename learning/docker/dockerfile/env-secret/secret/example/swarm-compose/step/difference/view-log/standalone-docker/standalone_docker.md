# Use Standalone Docker

- docker swarm manages services, but the actual workloads are run as containers on worker nodes;
- when a service is created in swarm, it deploys tasks, which are essentially containers running the specified image;
- each task is assigned to a node, and the node runs it as a regular container;


- because of this, you can interact with the container like any normal container using `docker ps`, `docker logs`, and `docker inspect`;
- however, swarm manages the lifecycle, so if a container crashes or is removed, swarm will restart it automatically;
- this is why logs can still be accessed using `docker logs <container_id>` even though the container is part of a swarm service.

**See the logs:**

1. find the container running the service:

    ```commandline
    docker ps --filter "name= my_stack_swarm-service"
    ```
   
    output:

    ```commandline
    CONTAINER ID   IMAGE              COMMAND                  CREATED          STATUS          PORTS     NAMES
    d9c50858e6fd   secret-swarm:1.0   "java -jar secret-sw…"   3 minutes ago   Up 3 minutes             my_stack_swarm-service.1.vd6ib1qi9tnems1kdaoq2gwmf
    ```

2. use docker logs to view logs from that container:

    ```commandline
    docker logs d9c50858e6fd
    ```

    **output:**

    ```commandline
   [SecretManager] secret: SuperSecurePassword12345
   [Main] secret: SuperSecurePassword12345
    ```
