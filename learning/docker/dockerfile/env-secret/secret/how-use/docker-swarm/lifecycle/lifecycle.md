# Secret Lifecycle

- you can list all secrets with `docker secret ls`;
- update a secret by deleting the old one and creating a new one (Docker Swarm doesn't allow in-place editing for security);
- delete with `docker secret rm <secret_name>`;


- deleting a secret with `docker secret rm <secret_name>` removes it from the Swarm, preventing it from being used in new or updated services;
- however, any running containers that were already using the secret will continue to have access to it until they stop or are redeployed.
