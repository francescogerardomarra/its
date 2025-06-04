# Do They Run Also Workloads?

- by default, manager nodes can run tasks, but in production it's common to restrict managers to only manage the swarm and not run workloads;
- in the context of Docker Swarm, a workload refers to the application containers or services that are scheduled and run on the cluster to perform tasks such as serving web traffic, processing data, or running backend logic;
- so we can say that manager nodes don't run workload containers;


- if you want a manager node **not** to run any replicas (i.e., workload containers), you must explicitly set it to drain using:

    ```commandline
    docker node update --availability drain <manager-node>
    ```
  
- **with the above command**:
  - no new tasks or services (running containers created by services) will be scheduled on this node;
  - existing tasks running on this node will be migrated (rescheduled) to other available nodes;
  - the node remains part of the swarm (i.e., not removed), but it will not run service tasks;
  - the command can be executed within both manager nodes and worker nodes.
- **by default, all nodes (even managers) are eligible to run tasks**.
