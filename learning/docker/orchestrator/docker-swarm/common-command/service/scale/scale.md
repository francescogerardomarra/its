# Scale a Service

**Command:**

```commandline
docker service scale <service-name>=<replica-count>
docker service update --replicas <replica-count> <service-name>
```

**Command explanation:**

* `docker service scale` is used to change the number of replicas (instances) of a specific service in a Docker Swarm;
* this command is straightforward and supports scaling multiple services at once:

    ```commandline
    docker service scale <service-name-1>=<replica-count> <service-name-2>=<replica-count> <service-name-3>=<replica-count>
    ```
* scaling multiple services at once is **not** possible with `docker service update` command;


* `docker service update` achieves the same result but is more general-purpose, allowing for additional updates like image changes or resource limits;
* both commands require the Docker engine to be in Swarm mode and the service to already exist;
* use `service scale` for quick replica adjustments and `service update` for more controlled or complex changes;
 

* under the hood, `docker service scale` uses `docker service update`.
