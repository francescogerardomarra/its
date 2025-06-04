# Secrets Don't Exist in Standard Docker

- without an orchestrator, like Swarm Mode or Kubernetes, Docker does not provide built-in secrets management;
- if you want to store a sensitive variable without an orchestrator, you can use these methods (**not recommended**):
  - manually load the secret using a volume, simulating the orchestrator secret behavior, which is not straightforward;
  - using environment variables (**not secure**);
  - use an external secret manager:
    - AWS Secrets Manager (see an example within the section [AWS secrets manager without orchestrator](../../../../index.md));
    - HashiCorp Vault;
    - Azure Key Vault / Google Secret Manager.
