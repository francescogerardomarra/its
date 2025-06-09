# Example CD Definition

- now that the CI process is complete, we aim to implement continuous deployment (CD) using Docker Hub's webhook functionality;
- the following example is theoretical because deploying our container requires a publicly accessible server, which may incur additional costs;
- Docker Hub can only deliver webhook notifications and trigger deployments if it can send requests to a publicly accessible endpoint;


- this example serves as a conceptual guide to demonstrate how Docker Hub **webhooks** can be integrated into a CD workflow;
- we want to automatically update a [Docker Swarm](../../../../../../orchestrator/docker-swarm/index.md) service running on a simple single-node cluster whenever a new image is pushed to Docker Hub;
- this ensures that the application always runs the latest version without manual intervention.
