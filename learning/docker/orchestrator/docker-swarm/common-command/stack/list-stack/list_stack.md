# List Stacks

**Command:**

```commandline
docker stack ls
```

**Command explanation:**

* lists all the Docker stacks currently running on the Docker Swarm manager node;
* provides basic information such as the stack name and number of running services in each stack;
* used to monitor and manage multi-service applications deployed with [docker stack deploy](../deploy/deploy.md);


* only works when Docker Swarm mode is enabled and active on the node;
* helps verify whether your stack deployments were successful;
* **stacks** are a concept that exists only when using Docker Swarm in conjunction with Docker Compose files (see [here](../../../stack/only-compose/only_compose.md)).
