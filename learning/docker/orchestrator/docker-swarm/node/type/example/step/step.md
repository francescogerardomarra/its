# Steps

1. create the leader manager node on `node 1`: see [here](../../manager/how-create/how_create.md);
2. add a follower manager node on `node 2`: see [here](../../manager/add-manager/add_manager.md);
3. create a worker node on `node 3` and `node 4`: see [here](../../worker/how-create/how_create.md);
4. run this command on a manager node to discover the nodes id:

    ```commandline
    docker node ls
    ```
    
    output:
    
    ```commandline
    ID                            HOSTNAME        STATUS    AVAILABILITY   MANAGER STATUS   ENGINE VERSION
    wbkfxctwut6tuvtiru0s2r5uu *   avolpe-pc       Ready     Active         Leader           28.1.1
    x8shk3jwe0dtwkwz5i9s91pgc     manager-2       Ready     Active         Reachable        28.1.1
    n2b7t9k3uw3h9wql1s6j8z0yr     worker-1        Ready     Active                          28.1.1
    u9a8j3h5ndq5zy6c84q4k7kzv     worker-2        Ready     Active                          28.1.1
    ```

5. run this command on `node 1` and `node 2`, to change their availability to `drain` so they cannot run workloads (containers):

    ```commandline
    docker node update --availability drain <manager-node-id>
    ```

    - you can also run the command twice from the same node to change the availability of both manager nodes, including the one you're not currently on;
    - when you run the above command in the **leader** node (`node 1`): it will execute the command;
    - when you run the above command in the **follower** node (`node 2`): it will pass the command to the leader node (`node 1`), that will execute it.

6. check if the manager nodes availability changed:

    ```commandline
    docker node ls
    ```
    
    output:
    
    ```commandline
    ID                            HOSTNAME        STATUS    AVAILABILITY   MANAGER STATUS   ENGINE VERSION
    wbkfxctwut6tuvtiru0s2r5uu *   avolpe-pc       Ready     Drain          Leader           28.1.1
    x8shk3jwe0dtwkwz5i9s91pgc     manager-2       Ready     Drain          Reachable        28.1.1
    n2b7t9k3uw3h9wql1s6j8z0yr     worker-1        Ready     Active                          28.1.1
    u9a8j3h5ndq5zy6c84q4k7kzv     worker-2        Ready     Active                          28.1.1
    ```

7. deploy a service with 4 replicas (from `node 1` or `node 2`):

    ```commandline
    docker service create --name myweb --replicas 4 nginx
    ```

    - since we used the [drain]() command in the manager nodes node, the replicas will be split among the worker nodes;  <!-- todo: link to drain command -->
    - if you run the above command in the **leader** node (`node 1`): it will execute the command;
    - if you run the above command in the **follower** node (`node 2`): it will pass the command to the leader node (`node 1`), that will execute it.

8. see in which nodes the replicas are running:

    ```commandline
    docker service ps myweb --no-trunc
    ```

   output:

    ```commandline
    ID                          NAME      IMAGE                                                                                  NODE        DESIRED STATE   CURRENT STATE                ERROR     PORTS
    a1b2c3d4e5f6g7h8i9j0k1l2m   myweb.1   nginx:latest@sha256:c15da6c91de8d2f436196f3a768483ad32c258ed4e1beb3d367a27ed67253e66   worker-1    Running         Running about a minute ago             
    n3o4p5q6r7s8t9u0v1w2x3y4z   myweb.2   nginx:latest@sha256:c15da6c91de8d2f436196f3a768483ad32c258ed4e1beb3d367a27ed67253e66   worker-2    Running         Running about a minute ago             
    z8y7x6w5v4u3t2s1r0q9p8o7n   myweb.3   nginx:latest@sha256:c15da6c91de8d2f436196f3a768483ad32c258ed4e1beb3d367a27ed67253e66   worker-1    Running         Running about a minute ago             
    l6k5j4h3g2f1e0d9c8b7a6z5y   myweb.4   nginx:latest@sha256:c15da6c91de8d2f436196f3a768483ad32c258ed4e1beb3d367a27ed67253e66   worker-2    Running         Running about a minute ago
    ```
