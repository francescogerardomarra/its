# Remove a Stack

**Command:**

```commandline
docker stack rm <stack-name>
```

**Command explanation:**

* removes a Docker stack identified by `<stack-name>` from the swarm cluster;
* stops and deletes all services, networks, and volumes defined in the stack;
* only works in Docker Swarm mode, which must be initialized before using the command;


* does not delete the actual Docker images used by the stack:
  * the image was pulled and stored locally on the node’s Docker image cache when the service was created or updated (see [here](../../../service/image-stored/image_stored.md));
* useful for cleaning up resources when the stack is no longer needed.
