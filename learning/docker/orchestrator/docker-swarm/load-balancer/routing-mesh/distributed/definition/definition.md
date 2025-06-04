# Distributed Load Balancer

- in most traditional architectures, the **load balancer is a single, centralized component** sitting **in front** of your services:

    ```text
    [Client]
       ↓
    [Load Balancer]
       ↓
    [App Server 1, 2, 3]
    ```

  - one load balancer (maybe 2 for HA);
  - all traffic goes through it;
  - it decides which backend server handles the request;
  - **risk:** the load balancer becomes a **single point of failure** unless it's carefully replicated or managed.

- in Docker Swarm, you're in a **distributed environment**, and the architecture encourages **high availability and fault tolerance**, even for the load balancer itself:

    ```text
    [Client]
       ↓
    [Any Swarm Node: Ingress Network Listener]
       ↓
    [Routing Mesh Overlay Network]
       ↓
    [Service Task (Container) on any Node]
    ```
  - there is no centralized load balancer;
  - the port exposed in one service is exposed for each node;
  - the node is able to redirect the request to the proper task (container) on the proper node;
  - the client can hit any node, and there is not a single point of failure, like in a centralized load balancer.
