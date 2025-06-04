# Containers Communication

- when a container is on the host network, it **cannot** communicate directly via Docker's usual internal networking (like with containers on the bridge network) using container names or Docker DNS;
- to communicate with other containers, it has to use host IP address (e.g., localhost or `127.0.0.1`);
- if the other container publishes a port to the host, then the host-network container can access it via:
    ```commandline
    localhost:EXPOSED_PORT
    ```


- **if container A is on the default bridge or used-defined network and does not expose any ports to the host, and container B is on the host network, then container B cannot access container A.**

**Example:**
- container A (on default bridge network, exposes port `5000`):

    ```commandline
    docker run -d -p 5000:5000 myapp
    ```


- container B (on host network):

    ```commandline
    docker run --rm --network host mytool curl http://localhost:5000
    ```

  - this works because myapp exposed port `5000` to the host, and the host-network container can access that just like any other local process;
  - if container A doesn't expose any port to the host, there is no way for container B to access it, since it cannot use Docker network.
