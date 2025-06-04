# Initialize Docker Swarm

- open a terminal within any folder and run thin command:

    ```commandline
    docker swarm init
    ```

- `docker swarm init` is used to initialize a new Docker Swarm mode cluster;
- it turns the current Docker host into a Swarm manager, allowing it to orchestrate containerized applications;
- after initialization, the node becomes the leader of the Swarm, meaning it can manage worker nodes and deploy services;


- running `docker swarm init` does not stop or remove existing containers, but it changes how the Docker engine schedules and manages containers;
- after initialization, the Docker engine operates in Swarm mode, meaning it schedules services instead of running standalone containers by default;
- you can still use normal Docker commands like `docker run`, but Swarm mode features such as built-in load balancing, service replication, and cluster management become available;


- so it extends the current Docker engine with additional orchestration features instead of replacing its existing functionality.