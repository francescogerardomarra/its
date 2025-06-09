# Example

- imagine you deploy a service in Swarm:

    ```commandline
    docker service create --replicas 3 nginx
    ```
- all the managers **cannot** run workloads ([drain](../../../../common-command/node/availability/drain/drain.md) command was executed on them);
- the manager decides where to place the three replicas;


- it assigns them to available worker nodes;
- each worker node runs one or more nginx container replicas as instructed.
