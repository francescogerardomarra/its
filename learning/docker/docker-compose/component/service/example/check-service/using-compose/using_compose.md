# Using Docker Compose

- enter the project root folder with a terminal (this command must be `docker-compose.yml` folder since Docker Compose needs to identify the services);
- run this command:

    ```commandline
    docker compose ps
    ```

- output:

    ```commandline
    NAME       IMAGE                 COMMAND                  SERVICE   CREATED          STATUS          PORTS
    my_app     compose-example-app   "java -jar compose-e…"   app       41 minutes ago   Up 39 minutes   0.0.0.0:5000->5000/tcp
    my_db      mysql:latest          "docker-entrypoint.s…"   db        41 minutes ago   Up 39 minutes   0.0.0.0:3306->3306/tcp, 33060/tcp
    my_redis   redis:latest          "docker-entrypoint.s…"   redis     41 minutes ago   Up 39 minutes   0.0.0.0:6379->6379/tcp
    ```

**Above command explanation:**

Lists all the running and stopped containers defined in the Docker Compose file.
