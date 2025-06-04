# Before Proceeding

- the concept of **Docker Secrets** is only available when using an orchestrator (e.g., Docker Swarm or Kubernetes);
- without an orchestrator, securely passing sensitive data is not straightforward, requiring workarounds such as file-based secrets, external secret managers, or encrypted environment variables;
- **all the concepts described in this chapter apply to containers managed by an orchestrator**.
