# `docker service scale`

- this command is used to set the number of replicas for a Docker Swarm service during runtime;
- in case the number of replicas is set within the `docker-compose.yml` file, this command will override it, but only at runtime;
- command:

    ```commandline
    docker service scale <stack_name>_<service_name>=<num_of_replicas>
    ```


- if you redeploy the stack, the number of replicas will reset to the value defined in the `docker-compose.yml` file;
- redeploying the stack does not delete the existing one, it only updates services that have changed.

**Example:**

- `docker-compose.yml` file:

    ```commandline
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

- open a terminal within the above `docker-compose.yml` file and run this command to deploy the stack to Docker Swarm:

    ```commandline
    docker stack deploy -c docker-compose.yml mystack
    ```

- check the number of replicas of `web` service:

    ```commandline
    docker stack ps mystack
    ```

    output:

    ```commandline
    ID             NAME            IMAGE             NODE        DESIRED STATE   CURRENT STATE            ERROR     PORTS
    14fdarw7nd3c   mystack_db.1    postgres:latest   avolpe-pc   Running         Running 37 seconds ago             
    o3r7m85i9lzf   mystack_web.1   nginx:latest      avolpe-pc   Running         Running 33 seconds ago             
    488md0l731hz   mystack_web.2   nginx:latest      avolpe-pc   Running         Running 33 seconds ago             
    w9471f6biv6d   mystack_web.3   nginx:latest      avolpe-pc   Running         Running 33 seconds ago
    ```

    as you can see `web` has 3 replicas, as we set within the `docker-compose.yml` file.

- increase the number of replicas at runtime to 5:

    ```commandline
    docker service scale mystack_web=5
    ```

- check if the number of replicas has increased:

    ```commandline
    docker stack ps mystack
    ```
    
    output:
    
    ```commandline
    ID             NAME            IMAGE             NODE        DESIRED STATE   CURRENT STATE                ERROR     PORTS
    14fdarw7nd3c   mystack_db.1    postgres:latest   avolpe-pc   Running         Running about a minute ago             
    o3r7m85i9lzf   mystack_web.1   nginx:latest      avolpe-pc   Running         Running about a minute ago             
    488md0l731hz   mystack_web.2   nginx:latest      avolpe-pc   Running         Running about a minute ago             
    w9471f6biv6d   mystack_web.3   nginx:latest      avolpe-pc   Running         Running about a minute ago             
    9galt0remvfq   mystack_web.4   nginx:latest      avolpe-pc   Running         Running 9 seconds ago                  
    uq0n1chmgej7   mystack_web.5   nginx:latest      avolpe-pc   Running         Running 9 seconds ago   
    ```
- redeploy the stack to restore all settings to the values defined in the `docker-compose.yml` file:

    ```commandline
    docker stack deploy -c docker-compose.yml mystack
    ```

- check if the number of replicas has returned to the value defined within `docker-compose.yml` file (3):

    ```commandline
    docker stack ps mystack
    ```
  
    output:
    
    ```commandline
    ID             NAME            IMAGE             NODE        DESIRED STATE   CURRENT STATE            ERROR     PORTS
    14fdarw7nd3c   mystack_db.1    postgres:latest   avolpe-pc   Running         Running 10 minutes ago             
    o3r7m85i9lzf   mystack_web.1   nginx:latest      avolpe-pc   Running         Running 10 minutes ago             
    488md0l731hz   mystack_web.2   nginx:latest      avolpe-pc   Running         Running 10 minutes ago             
    w9471f6biv6d   mystack_web.3   nginx:latest      avolpe-pc   Running         Running 10 minutes ago  
    ```
