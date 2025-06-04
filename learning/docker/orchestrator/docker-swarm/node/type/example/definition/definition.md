# Example Definition

- in this example, we want to create 4 nodes:
  - 2 managers;
  - 2 workers;
- we **use** the [drain] command, so the manager nodes **cannot** run workloads (containers) and they'll just manage the swarm; <!-- todo: link to drain command -->
- we want to deploy a service called `myweb`, running nginx with 4 replicas;


- the replicas will be split among the 2 worker nodes (**good practice**).
