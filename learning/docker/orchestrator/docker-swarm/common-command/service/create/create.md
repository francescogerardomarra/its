# Create a Service

**Command:**

```commandline
docker service create --name <name> <image>
```

**Command explanation:**

* creates a new service in Docker Swarm mode with the specified name and image;
* assigns the service a unique name provided in place of `<name>`;
* uses the specified Docker image (replacing `<image>`) as the base for the service's containers;


* automatically schedules the service on available swarm nodes based on default or specified constraints;
* enables scaling, load balancing, and fault tolerance for the deployed service.
