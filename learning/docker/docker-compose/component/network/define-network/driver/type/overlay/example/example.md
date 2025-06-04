# Example

- in this example, we define an overlay network `my_overlay_network` within a Docker Swarm cluster on host 1;
- a node on host 2 joins the same Docker Swarm cluster and gains access to the overlay network.

**Host 1:**

- `docker-compose.yml` file:

    ```yaml
    version: '3.9'
    
    services:
      app:
        image: nginx:latest
        container_name: my_nginx
        ports:
          - "8080:80"
        networks:
          - my_overlay_network
    
    networks:
      my_overlay_network:
        driver: overlay
    ```

- deploy the stack to Docker Swarm:

    ```commandline
    docker stack deploy -c docker-compose.yml my_stack
    ```

**Host 2:**

- join the Swarm:

    ```commandline
    docker swarm join --token SWMTKN-1-xxxxxxx <manager_ip>:2377
    ```

  the command is used to add a worker or manager node to an existing Docker Swarm cluster by connecting to the manager node at the specified IP address and port (`2377`) using the provided join token (a unique identifier that grants permission to join the Swarm as a worker or manager).

- `docker-compose.yml` file:

    ```yaml
    version: '3.9'
    
    services:
      backend:
        image: httpd:latest
        container_name: my_backend
        ports:
          - "9090:80"
        networks:
          - my_overlay_network
    
    networks:
      my_overlay_network:
        external: true
    ```
- deploy the stack to Docker Swarm:

    ```commandline
    docker stack deploy -c docker-compose.yml my_backend_stack
    ```
