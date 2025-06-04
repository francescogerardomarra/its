# Using Docker Compose

In this example, we are going to stop the docker compose stack created within the [previous](../../check/example/check/check.md) example:
- open a terminal within the `docker-compose.yml` folder and run:

    ```commandline
    docker compose down
    ```

    this will clean up any existing networks associated with the stack.
- output:

    ```commandline
    [+] Running 7/7
     ✔ Container service1                Removed                                                                                                                                                                            0.2s 
     ✔ Container service2                Removed                                                                                                                                                                            1.3s 
     ✔ Container service3                Removed                                                                                                                                                                            0.0s 
     ✔ Network desktop_custom_network_1  Removed                                                                                                                                                                            0.2s 
     ✔ Network desktop_custom_network_2  Removed                                                                                                                                                                            0.3s 
     ✔ Network desktop_custom_network_3  Removed                                                                                                                                                                            0.4s 
     ✔ Network desktop_default           Removed  
    ```
