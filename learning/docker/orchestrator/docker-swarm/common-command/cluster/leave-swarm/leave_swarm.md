# Leave a Swarm

**Command:**

```commandline
docker swarm leave
docker swarm leave --force   # (if manager)
```

**Command explanation:**

* `docker swarm leave` is used to remove a node from a Docker Swarm cluster;
* this command is typically used on worker nodes to disconnect them from the swarm;
* if the node is a manager, this command will fail if it is the last manager in the swarm, it will succeed otherwise;


* `docker swarm leave --force` forces a manager node to leave the swarm even if it's the last manager;
* this is useful when dismantling or resetting a swarm entirely.
