# With Name

In this example, we show that Docker uses the name we set for the volume.

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
        name: my-example-volume
    ```

2. check the volume name assigned by Docker

    ```commandline
    docker volume ls
    ```

   output:

    ```commandline
    DRIVER    VOLUME NAME
    local     my-example-volume
    ```
