# Bypass the Routing Mesh

- you can **bypass the routing mesh** so that accessing the published port on a specific node always connects directly to the service instance running on that node;
- this is referred to as **host mode**;
- keep in mind:
  - in this mode, you might access a node where no instance of the service is running on the specified port, which will result in a connection error;
  - if a node is running **multiple** replicas of the same service in host mode, **you cannot assign a fixed published port to each replica**;
  - instead, you must let Docker assign a **random published port** for each replica (by specifying only the `target` port and omitting the `published` port);
  - or ensure that only a single instance of the service runs on a given node, by using a [global service](../../../service/type/global/definition/definition.md) rather than a replicated one, or by using [placement constraints](../../../service/option/constraint/definition/definition.md). 


- when a service is published with `mode=host`, Docker's routing mesh is disabled;
- this means if a request is sent to a node that isn't running the service, **it won't be forwarded** to a node that is, the request will simply fail.


**Example:**

The following command creates a global service using **host mode**, bypassing the routing mesh:

```commandline
docker service create --name dns-cache \
  --publish published=8080,target=80,mode=host \
  --mode global \
  dns-cache
```
