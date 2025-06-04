# Use Case

**Goal:**

Collect logs from all nodes in your Docker Swarm cluster and forward them to a central logging system (e.g., Elasticsearch, Loki, etc.)

**Why use a global service:**

- you want one Fluentd agent running on every node to capture logs from containers on that node;
- as nodes join or leave the swarm, you don't want to manually manage agents, they should start/stop automatically.

**Example:**

```commandline
docker service create \
  --name fluentd-logger \
  --mode global \
  --mount type=bind,source=/var/log,destination=/var/log \
  --mount type=bind,source=/var/lib/docker/containers,destination=/var/lib/docker/containers,readonly \
  fluent/fluentd
```

- `--name fluentd-logger`: names the service fluentd-logger;
- `--mode global`: deploys one replica on every node in the swarm cluster;
- `--mount type=bind,source=/var/log,destination=/var/log`: 
  - mounts the host's `/var/log` directory into the container at the same path;
  - this gives Fluentd access to the system logs on the host.


- `--mount type=bind,source=/var/lib/docker/containers,destination=/var/lib/docker/containers,readonly`:
  - mounts the Docker containers' log directory from the host into the container;
  - `readonly`: the container can read but not write to this directory;
  - this lets Fluentd collect logs from Docker containers running on the host.
