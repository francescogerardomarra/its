# `docker stack deploy`

- this command is used to deploy a stack of services defined in a `docker-compose.yml` file using Docker Swarm;
- deploying the stack using Docker Swarm instead of standalone Docker Compose has several benefits, we list just 3, but there are more:
  - define the replicas within the `docker-compose.yml` file:
  
      ```yaml
      version: '3.8'
    
      services:
        web:
          image: nginx
          deploy:
            replicas: 3
      ```
  - Docker Swarm includes a built-in internal load balancer;
  - Docker Swarm monitors container health and performs automatic failure detection and recovery.
- command to deploy the stack to Docker Swarm:

    ```commandline
    docker stack deploy -c docker-compose.yml <stack_name>
    ```
**Example:**

- `docker-compose.yml` file:

    ```yaml
    version: '3.8'
    services:
      web:
        image: nginx
        deploy:
          replicas: 3
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

- open a terminal and run this command to enable Docker Swarm mode:

    ```commandline
    docker swarm init
    ```

- open a terminal within the above `docker-compose.yml` folder and run this command to deploy the stack to Docker Swarm:

    ```commandline
    docker stack deploy -c docker-compose.yml my-stack
    ```

- check if the stack is correctly deployed:

    ```commandline
    docker stack ls
    ```
    
    output:
    
    ```commandline
    NAME          SERVICES
    my-stack      2
    ```
- list the containers for the stack:

    ```commandline
    docker stack ps my-stack
    ```
    
    output:
    
    ```commandline
    ID             NAME             IMAGE             NODE        DESIRED STATE   CURRENT STATE            ERROR     PORTS
    mfc884ouq4mp   my-stack_db.1    postgres:latest   avolpe-pc   Running         Running 18 seconds ago             
    ooixoeiizuca   my-stack_web.1   nginx:latest      avolpe-pc   Running         Running 19 seconds ago             
    q4evciuipd48   my-stack_web.2   nginx:latest      avolpe-pc   Running         Running 19 seconds ago             
    kof4eajurr8w   my-stack_web.3   nginx:latest      avolpe-pc   Running         Running 19 seconds ago  
    ```
