# Promote a Node

**Command:**

```commandline
docker node promote <node-id>
```

**Command explanation:**

* promotes a Docker node from a worker to a manager role in a swarm;
* allows the promoted node to participate in managing the swarm, such as scheduling services and maintaining cluster state;
* requires that the node is already part of a Docker swarm;


* must be run from an existing manager node with proper permissions;
* uses `<node-id>` to specify the exact node being promoted.
