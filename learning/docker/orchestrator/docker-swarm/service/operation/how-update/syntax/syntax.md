# Syntax

The command syntax is this:

```commandline
docker service update [OPTIONS] SERVICE_NAME
```

**Examples:**

- update to a new image version:

    ```commandline
    docker service update --image myapp:latest my_service
    ```

- change the number of replicas:

    ```commandline
    docker service update --replicas 5 my_service
    ```

- set a new environment variable:

    ```commandline
    docker service update --env-add ENV_VAR=value my_service
    ```

- remove an environment variable:

    ```commandline
    docker service update --env-rm ENV_VAR my_service
    ```
