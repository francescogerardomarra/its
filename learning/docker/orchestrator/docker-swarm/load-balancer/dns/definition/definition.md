# DNS Round-Robin Definition

- DNS round-robin in Docker Swarm is a simple mechanism used to distribute traffic among service replicas for internal communication between services within the Swarm;
- it is not used for external traffic, which is handled by the routing mesh;
- it allows different services to call each other by name instead of by IPs;


- for example, if the `web` service wants to call the `api` service, it can simply use the service name `api`:

    ```commandline
    curl http://api:8080/data
    ```
