# Steps

1. create the leader manager node on `node 1`: see [here](../../how-create/how_create.md);
2. add a follower manager node on `node 2`: see [here](../../add-manager/add_manager.md);
3. deploy a service with 3 replicas (from `node 1` or `node 2`):

    ```commandline
    docker service create --name myweb --replicas 3 nginx
    ```

    - since we didn't use the [drain]() command in any node, the replicas will be split among the manager nodes;  <!-- todo: link to drain command -->
    - if you run the above command in the **leader** node (`node 1`): it will execute the command;
    - if you run the above command in the **follower** node (`node 2`): it will pass the command to the leader node (`node 1`), that will execute it.

4. see in which nodes the replicas are running:

    ```commandline
    docker service ps myweb --no-trunc
    ```

   output:

    ```commandline
    ID                          NAME      IMAGE                                                                                  NODE        DESIRED STATE   CURRENT STATE                ERROR     PORTS
    ou9yaydxf6i99zrpcm5vsn05o   myweb.1   nginx:latest@sha256:c15da6c91de8d2f436196f3a768483ad32c258ed4e1beb3d367a27ed67253e66   avolpe-pc   Running         Running about a minute ago             
    j0ukynqspasxz9c7wh4h4p85x   myweb.2   nginx:latest@sha256:c15da6c91de8d2f436196f3a768483ad32c258ed4e1beb3d367a27ed67253e66   avolpe-pc   Running         Running about a minute ago             
    djby3vmnkkwahfhpib7pw8fyt   myweb.3   nginx:latest@sha256:c15da6c91de8d2f436196f3a768483ad32c258ed4e1beb3d367a27ed67253e66   test-pc     Running         Running about a minute ago 
    ```
