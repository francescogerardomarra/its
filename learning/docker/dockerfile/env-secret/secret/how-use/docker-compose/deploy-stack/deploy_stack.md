# Deploy the Stack

- this command is used to deploy a stack of services in a Docker Swarm environment, managing multiple services as a single unit;
- **the command should be executed from a manager node in the Swarm, within the directory containing `docker-compose.yml` file;**
- the `-c` flag specifies the Docker Compose file (`docker-compose.yml`), which defines the services, networks, and volumes for the stack;


- the last argument (`my_stack`) is the name of the stack, which will be used to reference the deployed services;
- a stack is a collection of services, along with networks and volumes, that work together in Docker Swarm;
- this command works only in **Docker Swarm mode**, meaning `docker swarm init` must be run beforehand on the manager node;
 

- unlike `docker-compose up`, which is for standalone containers, this command creates replicated services and manages them in a distributed environment.

**Command to deploy a stack:**

```commandline
docker stack deploy -c docker-compose.yml my_stack
```
