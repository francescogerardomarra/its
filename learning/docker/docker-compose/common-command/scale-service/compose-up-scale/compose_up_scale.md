# `docker compose up --scale <service>=<num>`

- this command works like [docker compose up](../../create-start-service/compose-up/compose_up.md) but the `--scale` flag lets you specify how many instances (replicas) of a service you want to run;
- in standalone Docker Compose (i.e., not using Docker Swarm), you cannot define replicas directly in `docker-compose.yml`, instead, you use the `--scale` flag at runtime;
- integrating Docker Compose with Docker Swarm allows defining the [replicas](../../../component/service/replica/with-swarm/example/example.md) within the `docker-compose.yml` file;


- see the next [deploy](../../deploy/stack-deploy/stack_deploy.md) section to learn how to deploy a stack of services using Docker Swarm;
- you cannot use `docker compose up` when deploying with Docker Swarm; instead, you must use `docker stack deploy`.

**Example:**

- `docker-compose.yml` file:

    ```yaml
    version: '3.8'
    services:
      web:
        image: nginx
        volumes:
          - my-volume-one:/usr/share/nginx/html
      db:
        image: postgres
        environment:
          POSTGRES_USER: myuser
          POSTGRES_PASSWORD: mypassword
          POSTGRES_DB: mydatabase
    
    volumes:
      my-volume-one:
      my-volume-two:
    ```

- open a terminal within the above `docker-compose.yml` folder and run this command:

    ```commandline
    docker compose up -d --scale web=3 --scale db=2
    ```

- check the containers created:

    ```commandline
    docker compose ps
    ```
    
    output:
    
    ```commandline
    NAME            IMAGE      COMMAND                  SERVICE   CREATED          STATUS          PORTS
    desktop-db-1    postgres   "docker-entrypoint.s…"   db        15 seconds ago   Up 14 seconds   5432/tcp
    desktop-db-2    postgres   "docker-entrypoint.s…"   db        15 seconds ago   Up 14 seconds   5432/tcp
    desktop-web-1   nginx      "/docker-entrypoint.…"   web       15 seconds ago   Up 14 seconds   80/tcp
    desktop-web-2   nginx      "/docker-entrypoint.…"   web       15 seconds ago   Up 14 seconds   80/tcp
    desktop-web-3   nginx      "/docker-entrypoint.…"   web       15 seconds ago   Up 14 seconds   80/tcp
    ```
