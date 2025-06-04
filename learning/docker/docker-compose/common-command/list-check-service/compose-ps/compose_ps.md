# `docker compose ps`

- this command is used to list the containers that are defined and currently managed by your `docker-compose.yml` file;
- it shows:
  - the name of each container;
  - the service it belongs to (based on your `docker-compose.yml`);
  - its current state (like running, exited, etc.);
  - the ports it's exposing and mapping.

**Example:**

- `docker-compose.yml` file:

    ```yaml
    version: '3.8'
    
    services:
      web:
        image: nginx:latest   # or whatever web server/app you want
        ports:
          - "8080:80"         # Maps port 80 in the container to port 8080 on the host
    
      db:
        image: postgres:latest
        environment:
          POSTGRES_USER: user
          POSTGRES_PASSWORD: password
          POSTGRES_DB: mydatabase
        expose:
          - "5432"            # Makes port 5432 available to other services, but not mapped to host
    ```

- command to run:

    ```commandline
    docker compose up -d
    docker compose ps
    ```

- output:

    ```commandline
    NAME          IMAGE             COMMAND                  SERVICE   CREATED          STATUS         PORTS
    myapp-db-1    postgres:latest   "docker-entrypoint.s…"   db        10 seconds ago   Up 9 seconds   5432/tcp
    myapp-web-1   nginx:latest      "/docker-entrypoint.…"   web       10 seconds ago   Up 9 seconds   0.0.0.0:8080->80/tcp
    ```

  - the `docker-compose.yml` file is placed within a `myapp/` folder;
  - if you don't override the container name (using `container_name` field), the default name follows this rule: `<project-name>-<service-name>-<replica-number>` 
