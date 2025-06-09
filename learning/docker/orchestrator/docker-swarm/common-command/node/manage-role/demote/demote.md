# Demote a Node

**Command:**

```commandline
docker node demote <node-id>
```

**Command explanation:**

* demotes a manager node to a worker node in a Docker Swarm cluster;
* is used when a node no longer needs to participate in swarm management decisions;
* helps reduce the number of manager nodes, which can improve performance and stability;


* requires that the node being demoted is currently a manager and part of the swarm;
* should be used carefully, especially if demoting a [quorum-critical](../../../../node/type/manager/responsibility/raft-consensus/quorum/quorum.md) manager node.
