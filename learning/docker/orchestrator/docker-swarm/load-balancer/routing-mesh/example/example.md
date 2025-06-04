# Example

- this publishes port `8080` on all nodes:

    ```commandline
    docker service create \
    --name web \
    -p 8080:80 \
    --replicas 3 \
    nginx
    ```

- any request to port `8080` on any node in the swarm is routed to one of the nginx containers (on port `80`), regardless of where they are running.
