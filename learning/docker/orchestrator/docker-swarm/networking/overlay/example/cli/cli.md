# Example CLI

- in this example, we show how to create a service and connect it to an existing `overlay` network;
- if the network doesn't exist when you create the service, the `docker service create` or `docker service update` will throw an error.

**Steps:**

1. you must create an `overlay` network manually, `docker service create` and `docker service update` commands don't do it:

    ```commandline
    docker network create \
      --driver overlay \
      --attachable \
      my-overlay-network
    ```

   - `--driver overlay`: specifies it’s an `overlay` network for use in Swarm;
   - `--attachable` (optional): 
     - allows standalone containers to attach to the network too (not just services);
     - without the `--attachable` flag, only services (created via docker service create) can connect to the `overlay` network.

2. create the service `my-service`:

    ```commandline
    docker service create --name my-service --network my-overlay-network my-image
    ```
