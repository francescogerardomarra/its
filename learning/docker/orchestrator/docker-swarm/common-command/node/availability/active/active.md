# Active

**Command:**

```commandline
docker node update --availability active <node-id>
```

**Command explanation:**

* sets the availability status of a Docker Swarm node to "active";
* allows the node to receive and run tasks or services in the Swarm cluster;
* requires the specific `<node-id>` of the target node to apply the update;


* useful when re-enabling a node after maintenance or downtime;
* ensures the node is fully participating in workload distribution.
