# Inspect a Node

**Command:**

```commandline
docker node inspect <node-id>
```

**Command explanation:**

* retrieves detailed information about a specific node in a Docker Swarm cluster;
* requires the `<node-id>` or node name to identify which node to inspect;
* returns data in JSON format, including status, role (manager/worker), availability, and labels;


* useful for diagnosing issues, verifying configurations, or monitoring node health.
