# Example Definition

- in this example, we want to create 2 manager nodes;
- we **don't use** the [drain] command, so the manager nodes **can** run workloads (containers); <!-- todo: link to drain command -->
- we want to deploy a service called `myweb`, running nginx with 3 replicas;


- the replicas will be split among the 2 manager nodes (**not a good practice**, see [here](../prerequisite/prerequisite.md)).
