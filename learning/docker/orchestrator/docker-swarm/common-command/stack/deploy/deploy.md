# Deploy a Stack From a `docker-compose.yml` File

**Command:**

```commandline
docker stack deploy -c <docker-compose.yml> <stack-name>
```

**Command explanation:**

* uses Docker Swarm to deploy a group of services defined in a `docker-compose.yml` file;
* the `-c <docker-compose.yml>` option specifies the path to the Docker Compose file containing service definitions;
* `<stack-name>` is the name given to the stack, used to group related services under a single namespace;


* creates and manages services, networks, and volumes as defined in the `docker-compose.yml` file;
* is only applicable when Docker Swarm mode is enabled.

