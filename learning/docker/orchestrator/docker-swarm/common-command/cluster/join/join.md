# Join a Swarm (Worker or Manager)

**Command:**

```commandline
docker swarm join --token <token> <manager-ip>:2377
```

**Command explanation:**

* connects a Docker node to an existing Swarm cluster as a worker or manager, depending on the token used;
* `<token>` is a secret key generated by the manager node to authorize and authenticate new nodes;
* `<manager-ip>:2377` specifies the IP address and port of the Swarm manager node to which the new node should connect;


* port `2377` is the default port used for cluster management communications in Docker Swarm;
* the command must be run on the node that is joining the Swarm.
