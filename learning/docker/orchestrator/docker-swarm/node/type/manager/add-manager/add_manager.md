# Add Manager Nodes

**Prerequisite:**

- Docker installed on the manager nodes;
- all nodes in a Docker Swarm must be able to communicate with each other over the network (e.g., nodes in the same subnet or VPC).

**Steps:**

1. run this command on the [leader](../how-create/how_create.md) manager node:

    ```commandline
    docker swarm join-token manager
    ```
    
    output:
    
    ```commandline
    To add a manager to this swarm, run the following command:
    
        docker swarm join --token SWMTKN-1-5mpo4vepc5ue76n43vw8h1vw0z68z7mfqzf2t1hm7ot7z5l5k9-cxdpqtf5agfe73pqu8x1lyuch 192.168.1.11:2377
    ```

2. run the above **output** command in the new [follower](../responsibility/raft-consensus/role/role.md) manager node that you want to add to the swarm cluster:

    ```commandline
    docker swarm join --token SWMTKN-1-5mpo4vepc5ue76n43vw8h1vw0z68z7mfqzf2t1hm7ot7z5l5k9-cxdpqtf5agfe73pqu8x1lyuch 192.168.1.11:2377
    ```

   - **Docker Swarm determines whether you're adding a manager or a worker based on the token you use when running the `docker swarm join` command**.
