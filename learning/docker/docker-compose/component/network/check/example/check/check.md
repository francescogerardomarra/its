# Check

- in this example, we create 4 networks and 3 services;
- this example is useful to see different scenarios of using networks;
- Docker Compose only creates networks that are actively used by at least one service.

**Steps:**

1. place the following `docker-compose.yml` file in a folder you prefer on your machine:

    ```yaml
    version: '3.9'
    
    services:
      service1:
        image: nginx:latest
        container_name: service1
        # This service uses the default bridge network, no networks specified
    
      service2:
        image: httpd:latest
        container_name: service2
        networks:
          - custom_network_1
          - custom_network_2
    
      service3:
        image: alpine:latest
        container_name: service3
        networks:
          - custom_network_3
    
    networks:
      custom_network_1:
        driver: bridge
      custom_network_2:
        driver: bridge
      custom_network_3:
        driver: bridge
      custom_network_4:
        driver: bridge
    ```

2. open a terminal within the folder and run:

    ```commandline
    docker compose up -d
    ```

3. check the current networks:

    ```commandline
    docker network ls
    ```
    
    output:
    
    ```commandline
    NETWORK ID     NAME                            DRIVER    SCOPE
    0750287c4212   desktop_custom_network_1        bridge    local
    f673103cf1e3   desktop_custom_network_2        bridge    local
    75968ff0769f   desktop_custom_network_3        bridge    local
    1b367b298f2a   desktop_default                 bridge    local
    ```
    
    - `custom_network_4` is not created since it's not used by any service;
    - the name of the project folder (`desktop`) is placed before the name we set for the networks.
