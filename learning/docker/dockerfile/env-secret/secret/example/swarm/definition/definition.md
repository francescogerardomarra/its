# Docker Swarm Without Docker Compose Definition

- in this example, we are going to create a secret within Docker Swarm;
- **Docker Swarm** is Docker’s native container orchestration tool that allows you to deploy, manage, and scale containerized applications across a cluster of multiple Docker nodes while providing built-in load balancing, high availability, and automated task scheduling;
- a `.jar` program, will retrieve the secret and print it to console output;


- the `.jar` file will be containerized and run within a Docker Swarm service;
- a Docker Swarm service is an abstraction that allows you to define how a container should be managed (the number of replicas for example);
- within a single Docker Swarm service, can run only one container (with many replicas);


- if you want to run a different type of container you should create another Docker Swarm service.
