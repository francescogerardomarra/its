# Local

- network scopes in Docker Compose define the accessibility and visibility of containers within and across Docker hosts;
- local scope, often represented by the `bridge` network driver, limits network access to containers running on the same Docker host;
- containers within a `bridge` network can communicate with each other using container names as hostnames;
 

- containers on different `bridge` networks or different Docker hosts cannot directly communicate unless additional configuration (such as host networking or port mapping) is applied;
- by default, Docker Compose creates a `bridge` network for each service defined in the Compose file.
