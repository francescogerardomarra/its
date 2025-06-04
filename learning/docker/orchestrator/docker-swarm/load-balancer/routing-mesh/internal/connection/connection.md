# Connection Path Summary

- this is the connection path schema:

    ```text
    Client → Node (any node) → IPVS rule → Task (local or remote)
    ```


- the client sends a request to a node, depending on where the service task is running:
  - **if the task is local to the node**: Docker forwards the request directly to the container;
  - **if the task is on a remote node**: Docker proxies the request over the VXLAN-based overlay network.
- the task (container) receives the request on its internal port.
