# Cluster Management

- **maintains the desired state of the swarm**:
  - e.g., if a service is set to have 3 replicas and one container crashes, the manager will automatically restart it to maintain 3 replicas.
- **schedules tasks (containers) to run on nodes**:
  - e.g., when you deploy a service, the manager decides which worker nodes will run the containers based on resource availability.
- **monitors the health and status of worker nodes**:
  - e.g., if a worker node goes offline, the manager detects the failure and reschedules its tasks to other healthy nodes.


- **handles the creation, updating, or deletion of services**:
  - e.g., when you run docker service update to change the image version, the manager stops old containers and starts new ones with the updated version.
- **all the Swarm management commands (like `docker service create`, `update`, `rm`, etc.) must be launched within a manager node**;
- the commands can be launched from any manager node, no need to be the [leader](../raft-consensus/role/role.md).
