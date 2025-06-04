# How to Create

**Prerequisite:**

Docker installed on the node.

**Step:**

Run the following command on the node you want to make the [leader] manager: <!-- todo: link to leader chapter -->

```commandline
docker swarm init
```

output:

```commandline
Swarm initialized: current node (wbkfxctwut6tuvtiru0s2r5uu) is now a manager.

To add a worker to this swarm, run the following command:

    docker swarm join --token SWMTKN-1-5mpo4vepc5ue76n43vw8h1vw0z68z7mfqzf2t1hm7ot7z5l5k9-ajs5sv5f0ljefos06bmx5sgbi 192.168.1.11:2377

To add a manager to this swarm, run 'docker swarm join-token manager' and follow the instructions.
```

- the IP (`192.168.1.11` here) is the IP address of the manager node;
- the command includes a join token that worker nodes can use.
