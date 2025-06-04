# Rolling Updates

- usually, a production Swarm service has multiple replicas distributed across different nodes;
- in Docker Swarm, **rolling updates** are a deployment strategy that lets you **update services gradually**, rather than restarting or replacing all containers at once;
- when you update a service (like changing the image version), Swarm doesn't shut everything down immediately, instead, it:

  1. stops and removes a **few old containers** (tasks) at a time;
  2. starts **new ones with the updated version**;
  3. waits for them to become healthy;
  4. repeats the process until all old containers are replaced.


- you can control how gradual this is using parameters like:
  - `--update-parallelism`: how many containers (in each batch) to update at once;
  - `--update-delay`: how long Swarm waits after each batch is updated, before checking health and starting the next batch.
- **benefit**: 
  - safer updates, easier rollback if something goes wrong;
  - since there is a specific command for performing rollback after the update: `docker service update --rollback <service_name>`. <!-- todo: link to common commands section -->
