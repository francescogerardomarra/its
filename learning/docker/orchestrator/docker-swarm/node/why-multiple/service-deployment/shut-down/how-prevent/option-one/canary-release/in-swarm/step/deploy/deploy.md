# Deploy Two Versions of the Service

- deploy the stable version of your application (e.g., `myapp:1.0`) as one service;
- deploy the canary version (e.g., `myapp:2.0`) as a separate service;
- example:
    - **stable version**:

        ```commandline
        docker service create --name myapp-v1 --replicas 5 myapp:1.0
        ```
    - **canary version**:

        ```commandline
        docker service create --name myapp-v2 --replicas 1 myapp:2.0
        ```