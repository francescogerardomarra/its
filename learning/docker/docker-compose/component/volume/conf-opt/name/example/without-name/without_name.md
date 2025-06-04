# Without Name

In this example, we show the default name given by Docker to a volume.

**Steps:**

1. create a `docker-compose.yml` and run `docker compose up -d` command:

    ```yaml
    version: '3'
    services:
      app:
        image: nginx
        volumes:
          - mydata:/data
    
    volumes:
      mydata:
    ```

2. check the volume name assigned by Docker

    ```commandline
    docker volume ls
    ```
    
    output:
    
    ```commandline
    DRIVER    VOLUME NAME
    local     desktop_mydata
    ```
