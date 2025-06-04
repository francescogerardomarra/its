# Worker

**Command:**

```commandline
docker swarm join-token worker
```

**Command explanation:**

* retrieves the command needed to add a new worker node to an existing Docker Swarm cluster;
* is run on a manager node within the swarm to generate the join token;
* displays the `docker swarm join` command along with a secure token unique to the swarm;


* helps securely connect worker nodes without granting manager-level permissions.
