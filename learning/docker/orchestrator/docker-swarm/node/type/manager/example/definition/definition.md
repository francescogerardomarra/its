# Example Definition

- in this example, we want to create 2 manager nodes;
- we **don't use** the [drain](../../../../../common-command/node/availability/drain/drain.md) command, so the manager nodes **can** run workloads (containers);
- we want to deploy a service called `myweb`, running nginx with 3 replicas;


- the replicas will be split among the 2 manager nodes (**not a good practice**, see [here](../prerequisite/prerequisite.md)).
