# Problem in a Distributed Environment

- consider you deploy using this [docker-compose.yml] file; <!-- todo: link to the example file -->
- when you deploy this using:

    ```commandline
    docker stack deploy -c docker-compose.yml mystack
    ```

- Docker Swarm will:

  1. create the named volume `myvolume` on each node where the service is scheduled to run;
  2. if a task is scheduled on a node that does not yet have `myvolume`, Docker automatically creates it locally on that node;
  3. the volume is not shared or replicated automatically across nodes.


- if Swarm moves the container to another node (e.g., due to failure or update), it will:

  1. check if `myvolume` exists on the new node;
  2. if it doesn't, it creates a new empty volume with the same name;
  3. but the data is not transferred, so any data on the original node’s volume is lost unless you're using shared storage like [NFS] or [volume plugins]. <!-- todo: link to nfs --> <!-- todo: link to volume plugins -->
