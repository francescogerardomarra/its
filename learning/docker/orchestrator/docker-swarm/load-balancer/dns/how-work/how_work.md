# How it Works

When you deploy a **service** in Docker Swarm with multiple replicas (containers), Docker Swarm:
1. assigns each container a **unique IP address** in the overlay network;
2. registers all those container IPs under the **same DNS name** (the service name);
3. when you query the service name (e.g., `web-service`) inside the Swarm network, the DNS system returns **a list of IPs**, one for each container.

**Example:**

- this is an example DNS lookup result:

    ```
    $ getent hosts web-service
    10.0.0.2   web-service
    10.0.0.3   web-service
    10.0.0.4   web-service
    ```

- `getent hosts <name>` is a general Linux tool to resolve hostnames;
- we used this tool to query the Docker Swarm DNS server within a container;
- depending on the system, Docker's internal DNS server might:
    - return **all IPs in a round-robin order**;
    - or return **just one IP**, changing which one it returns on subsequent queries.


- see how IP is chosen [here](../how-ip/how_ip.md).
