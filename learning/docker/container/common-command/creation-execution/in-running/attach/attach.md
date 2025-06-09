# `docker attach`

- the `docker attach` command is used to attach your terminal to a running container's main process, allowing you to interact with it directly (like stdin/stdout);
- it essentially connects your terminal to the container’s standard input/output streams;
- you can use `docker attach` to reconnect to an interactive shell (like in an Ubuntu container), or to a container that simply prints output, as shown in the following example.

**Example:**

This example demonstrates how to run a Docker container in attached mode to continuously log timestamps, disconnect from it without stopping the container, and later reattach to resume viewing the live output:

1. run a container in attached mode (without `-d` flag) that prints the current date time each second:
    
    ```commandline
    docker run --name my-time-logger nfrankel/simplelog:1
    ```
    
    output:
    
    ```commandline
    [de77a44881aa] Tue Jun  3 09:32:45 UTC 2025
    [de77a44881aa] Tue Jun  3 09:32:46 UTC 2025
    [de77a44881aa] Tue Jun  3 09:32:47 UTC 2025
    [de77a44881aa] Tue Jun  3 09:32:48 UTC 2025
    ```
2. close the terminal by GUI (see [here](../disconnect/non-interactive/non_interactive.md), **not** press `ctrl + C` otherwise you kill the container);
3. attach to the `my-time-logger` container to reconnect and enter container attach mode again:

    ```commandline
    docker attach my-time-logger
    ```

    output:
    
    ```commandline
    [de77a44881aa] Tue Jun  3 09:35:05 UTC 2025
    [de77a44881aa] Tue Jun  3 09:35:06 UTC 2025
    [de77a44881aa] Tue Jun  3 09:35:07 UTC 2025
    [de77a44881aa] Tue Jun  3 09:35:08 UTC 2025
    ```


