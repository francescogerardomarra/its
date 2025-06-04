# Where the Pulled Images Are Stored

When an image is pulled (using [docker service create](../../common-command/service/create/create.md) or [docker service update](../../common-command/service/update/update.md)):
- each worker node (or manager node, if it's scheduled to run a replica) pulls the image from the registry (e.g., Docker Hub or a private registry) only if it is assigned a task (replica) for the service;
- the image is pulled and stored locally on the node’s Docker image cache (typically under `/var/lib/docker`).
