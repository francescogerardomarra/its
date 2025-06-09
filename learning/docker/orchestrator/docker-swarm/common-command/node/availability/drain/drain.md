# `Drain`

**Command:**

```commandline
docker node update --availability drain <node-id>
```

**Command explanation:**

* sets the specified Docker Swarm node to "drain" mode, meaning it will not receive any new tasks or containers;
* allows existing tasks on the node to be rescheduled on other available nodes;
* is typically used before performing maintenance or shutting down the node;


* helps ensure service availability by redistributing workloads safely;
* requires the `<node-id>` of the target node as an argument.
