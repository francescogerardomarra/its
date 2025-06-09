# Caveats

- while this example uses Docker Swarm for simplicity, the same concept of automating deployments based on new Docker images can also be applied in more complex environments like Kubernetes;
- however, instead of relying on Docker Hub webhooks, Kubernetes deployments are typically managed using tools that monitor image updates or Git repository changes;
- tools such as image update controllers (e.g., Flux or Argo CD) can automatically detect new image versions and trigger deployments as part of a GitOps workflow, where Git acts as the single source of truth for the system's desired state.
