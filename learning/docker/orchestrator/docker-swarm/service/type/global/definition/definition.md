# Global Definition

- in Docker Swarm, a global service is a special type of service that ensures one task (container) runs on every node in the swarm cluster;
- unlike [replicated](../../replicated/definition/definition.md) services (where you specify how many instances to run), global services automatically **run exactly one container on every node in the swarm**;
- the global service doesn't run on [drain](../../../../common-command/node/availability/drain/drain.md) nodes.

**Example:**

```commandline
docker service create \
  --name sys-monitor \
  --mode global \
  alpine \
  sh -c "while true; do top -b -n1; sleep 5; done"
```

- `--name sys-monitor`: names the service;
- `--mode global`: ensures one task runs per node;
- `alpine`: the base image (lightweight);


- `sh -c "...top..."`: keep showing the system’s resource usage (CPU, memory, etc.) every 5 seconds;
- in Docker, when you pass a command after the image name (like `sh -c "..."`), it overrides the image’s default [CMD](../../../../../../dockerfile/common-command/cmd/cmd.md).
