# Syntax

The following command ensures that the service runs only on nodes where the label `node.labels.key` is set to `value`:

- during service creation:

    ```commandline
    docker service create --name my-service --constraint 'node.labels.key==value' <image>
    ```

- during service update:

    ```commandline
    docker service update --constraint-add 'node.labels.key==value' my-service
    ```
