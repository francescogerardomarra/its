# Set the Replicas Number (`--replicas`)

- run this command on a manager node:

    ```commandline
    docker service create --replicas 3 nginx
    ```

- the above command creates a service running nginx with 3 replicas, distributed across the active nodes in the Swarm cluster.
