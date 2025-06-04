# List All Nodes

**Command:**

```commandline
docker node ls
```

**Command explanation:**

* lists all nodes in a Docker Swarm cluster;
* displays information such as hostname, status, availability, and role of each node;
* helps identify which nodes are managers or workers;


* is only available when the current Docker host is part of a Swarm;
* commonly used to monitor and manage the Swarm setup;
* this command can be executed just on a [manager node]. <!-- todo: link to manager nodes chapter -->
