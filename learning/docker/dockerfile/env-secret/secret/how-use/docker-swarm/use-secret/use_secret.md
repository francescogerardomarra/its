# Use the Secret in a Service

1. **creates a new service:**
    - `docker service create --name my_service`;
    - deploys a new **Swarm service** named `my_service`, which can run on one or more worker nodes.
2. **uses a Docker secret:**
    - `--secret db_password`;
    - attaches a [pre-existing Docker secret](../create-secret/create_secret.md) (`db_password`) to the service, allowing it to securely access sensitive data (e.g., database credentials);
    - the secret is mounted as a file inside the container at `/run/secrets/db_password`.
3. **deploys the specified image:**
    - `my_image`;
    - uses `my_image` as the container image, pulling it from a **local source or a registry** (e.g., Docker Hub, a private registry, or an internal repository) if the image is not present locally;
    - to use a different registry in Docker Swarm, specify the full registry URL in `docker service create` (e.g., `myregistry.com/my_image:latest`), ensure all nodes can access it, and if it's private, first authenticate using `docker login`, then use `--with-registry-auth` to securely pass credentials saved during `docker login` to Swarm nodes for pulling the image without prompting.
4. **runs the Service in Docker Swarm:**
    - the service is managed by Swarm, meaning it **automatically schedules, scales, and maintains** containers across available nodes.

**This command must be launched from a Swarm Manager node**, either locally or via SSH, as only manager nodes can create or modify services in a Docker Swarm cluster.

**Command to Use the Secret in a Service:**

```commandline
docker service create --name my_service --secret db_password my_image
```
