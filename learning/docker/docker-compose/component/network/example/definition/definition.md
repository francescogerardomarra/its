# Example Definition

- in this example, we are going to create 2 services (`app` and `db`);
- `app` will simply ping `db`, accessing it by service name.

**Steps:**

1. place the following `docker-compose.yml` file in a folder you prefer on your machine:

    ```yaml
    version: '3.9'
    
    services:
      app:
        image: busybox
        command: ["sh", "-c", "ping db -c 3"]
        depends_on:
          - db
    
      db:
        image: postgres:latest
        environment:
          POSTGRES_USER: user
          POSTGRES_PASSWORD: password
    ```
2. open the folder within a terminal and run:

    ```commandline
    docker compose up -d
    ```
3. check the app logs:

    ```commandline
    docker compose logs app
    ```

   output:

    ```commandline
    app-1  | PING db (172.21.0.2): 56 data bytes
    app-1  | 64 bytes from 172.21.0.2: seq=0 ttl=64 time=0.108 ms
    app-1  | 64 bytes from 172.21.0.2: seq=1 ttl=64 time=0.069 ms
    app-1  | 64 bytes from 172.21.0.2: seq=2 ttl=64 time=0.059 ms
    ```

   as you can see the `db` service is visible by `app` service since they are connected to the same network.
