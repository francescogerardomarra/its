# Manager

**Command:**

```commandline
docker swarm join-token manager
```

**Command explanation:**

* retrieves the command needed to add a new manager node to an existing Docker Swarm cluster;
* displays the join-token along with the full `docker swarm join` command tailored for manager nodes;
* used by administrators when expanding the Swarm with additional manager nodes;


* helps ensure secure and authenticated joining of nodes using a unique token.
