# Named Volumes and Bind Mounts

When you talk about "local volumes" in Docker Swarm, you're generally referring to both named volumes (on default local driver) and bind mounts, because both types of storage are node-local unless configured otherwise (e.g., with shared storage drivers).

**Named Volumes:**

- [named volumes](../../../../../../volume/mount/type/named/how-work/how_work.md) are created and managed by Docker on the local filesystem;
- stored under `/var/lib/docker/volumes/`;
- **not automatically shared across nodes** in the swarm;


- good for single-node tasks (see [here](../problem/problem.md));
- for multi-node scenarios, use [shared volumes](../../shared/shared.md);

**Bind mounts:**

- [bind volumes](../../../../../../volume/mount/type/bind/how-work/how_work.md) are supported, but path must exist on all nodes where the service could run;
- not portable, depends on specific host file system layout;
- you lose Swarm's abstraction; more error-prone;


- **use case**: only if you know your containers will run on specific nodes (e.g., with placement [constraints](../../../../service/option/constraint/definition/definition.md) or labels) and the path is consistent.
