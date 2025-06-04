# Multiple Constraints

- you can add multiple constraints like this:

    ```commandline
    docker service create \
      --name nginx \
      --constraint 'node.labels.type==manager' \
      --constraint 'node.labels.zone==us-east' \
      nginx
    ```

- this acts as an `AND` condition: the node must meet all constraints.
