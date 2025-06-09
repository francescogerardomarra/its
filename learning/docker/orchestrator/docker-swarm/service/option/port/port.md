# Publish Container Port (`-p`)

- run this command on a manager node:

    ```commandline
    docker service create -p 8080:80 nginx
    ```

- by default, Swarm uses the [routing mesh](../../../load-balancer/routing-mesh/how-work/how_work.md), which means:
  - port `8080` is opened on every node in the Swarm;
  - any request to any node on port `8080` is routed to one of the service's containers (replicas), even if that node doesn't have a running replica;
  - for example:
    - the nginx container is only running on `Node A`, you send a request from `Node C` to `Node B:8080`;
    - Swarm will route that request to the Nginx container on `Node A`, transparently.
