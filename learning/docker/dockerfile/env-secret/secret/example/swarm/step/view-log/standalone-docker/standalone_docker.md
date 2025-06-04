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
    docker ps --filter "name=swarm-service"
    ```
   
    output:

    ```commandline
    CONTAINER ID   IMAGE              COMMAND                  CREATED          STATUS          PORTS     NAMES
    9f3d699b0191   secret-swarm:1.0   "java -jar secret-sw…"   16 minutes ago   Up 16 minutes             swarm-service.1.mjrmd5ci9eswpul00fmaek7zx
    ```

2. use docker logs to view logs from that container:

    ```commandline
    docker logs 9f3d699b0191
    ```

    **output:**

    ```commandline
    [SecretManager] secret: MySecretPassword1234_
    [Main] secret: MySecretPassword1234_
    ```
