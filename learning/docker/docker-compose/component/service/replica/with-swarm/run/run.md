# Run

1. initialize Docker Swarm:

    ```commandline
    docker swarm init
    ```

2. open a terminal within the project root folder and build the image:

    ```commandline
    docker build -t compose-example-image:1.0 .
    ```    
   
3. deploy the stack to Docker Swarm:

   ```commandline
   docker stack deploy -c docker-compose.yml compose-example-stack
   ```
