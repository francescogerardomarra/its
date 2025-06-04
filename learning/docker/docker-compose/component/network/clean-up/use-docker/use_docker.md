# Using Standalone Docker

- in this example, we are going to stop the Docker Compose stack created within the [previous](../../check/example/check/check.md) example;
- **you can remove just the networks currently unused by any container**;
- if you try to remove a currently used network, you'll get an error.

**Steps:**

1. check the active networks, open a terminal and run:

    ```commandline
    docker network ls
    ```
    
    output:
    
    ```commandline
    NETWORK ID     NAME                            DRIVER    SCOPE
    450820a037ed   desktop_custom_network_1        bridge    local
    1bdddab2c769   desktop_custom_network_2        bridge    local
    8dbf6dc87721   desktop_custom_network_3        bridge    local
    63a42d7e4317   desktop_default                 bridge    local
    ```

2. remove `desktop_custom_network_3`:

    ```commandline
    docker network rm desktop_custom_network_3
    ```

3. check the active networks again, to see if `desktop_custom_network_3` disappeared:

    ```commandline
    docker network ls
    ```
    
    output:
    
    ```commandline
    NETWORK ID     NAME                            DRIVER    SCOPE
    450820a037ed   desktop_custom_network_1        bridge    local
    1bdddab2c769   desktop_custom_network_2        bridge    local
    63a42d7e4317   desktop_default                 bridge    local
    ```

    `service3` exits immediately after creation, so `desktop_custom_network_3` remains unused.

Alternatively, you can remove all unused networks with just one command:

```commandline
docker network prune
```

output:

```commandline
WARNING! This will remove all custom networks not used by at least one container.
Are you sure you want to continue? [y/N] y
Deleted Networks:
desktop_custom_network_3
```
