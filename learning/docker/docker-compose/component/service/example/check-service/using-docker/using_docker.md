# Using Standalone Docker

- Docker Compose uses the Docker engine to create and manage the containers, which means they are actual Docker containers running on the system;
- the `docker ps` command lists all active Docker containers managed by the Docker engine, regardless of how they were created;
- since Docker Compose containers are standard Docker containers, they appear in the list when running `docker ps` or `docker container ls`;


- open a terminal and run this command:

    ```commandline
    docker ps
    ```

- output:

    ```commandline
    CONTAINER ID   IMAGE                 COMMAND                  CREATED          STATUS             PORTS                               NAMES
    74bb75f4fba9   compose-example-app   "java -jar compose-e…"   54 minutes ago   Up 52 minutes      0.0.0.0:5000->5000/tcp              my_app
    be88bde80200   redis:latest          "docker-entrypoint.s…"   54 minutes ago   Up 52 minutes      0.0.0.0:6379->6379/tcp              my_redis
    0368d9828a00   mysql:latest          "docker-entrypoint.s…"   54 minutes ago   Up 52 minutes      0.0.0.0:3306->3306/tcp, 33060/tcp   my_db
    ```
