# Using Standalone Docker

- since the services are executed as regular containers on your machine, you can analyze the logs using standalone Docker;
- open a terminal and discover the name of the app service containers (just one in this case, since there's only 1 replica):

    ```commandline
    docker ps
    ```

    output:
    
    ```commandline
    CONTAINER ID   IMAGE                 COMMAND                  CREATED          STATUS          PORTS                               NAMES
    0b17d2ad76c6   compose-example-app   "java -jar compose-e…"   12 minutes ago   Up 12 minutes   0.0.0.0:5000->5000/tcp              my_app
    ab9945f68f16   mysql:latest          "docker-entrypoint.s…"   22 minutes ago   Up 22 minutes   0.0.0.0:3306->3306/tcp, 33060/tcp   my_db
    f0b23d2c74e0   redis:latest          "docker-entrypoint.s…"   22 minutes ago   Up 22 minutes   0.0.0.0:6379->6379/tcp              my_redis
    ```

- analyze the logs:

    ```commandline
    docker logs my_app
    ```
    
    output:
    
    ```commandline
    Hello World 1
    Hello World 2
    Hello World 3
    Hello World 4
    Hello World 5
    ```
