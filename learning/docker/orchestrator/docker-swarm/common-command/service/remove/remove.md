# Remove a Service

**Command:**

```commandline
docker service rm <service-id>
```

**Command explanation:**

* removes a running service in a Docker Swarm environment;
* takes the `<service-id>` as an argument, which is the name or ID of the service to remove;
* stops and removes all tasks associated with the specified service;


* is irreversible; once removed, the service must be redeployed to run again.
