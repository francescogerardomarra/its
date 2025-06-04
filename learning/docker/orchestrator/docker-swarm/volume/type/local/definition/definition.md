# Local Volumes (Default) Definition

- created and managed by Docker on the local filesystem;
- stored under `/var/lib/docker/volumes/`;
- **not automatically shared across nodes** in the swarm;


- good for single-node tasks (see [here](../problem/problem.md);
- for multi-node scenarios, use [shared volumes]. <!-- todo: link to shared volumes -->
