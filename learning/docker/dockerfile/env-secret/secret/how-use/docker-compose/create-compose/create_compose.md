# Create `docker-compose.yml` File

- create the following `docker-compose.yml` file;
- `services` section:
  - defines a single service app that runs the Docker image `my_app`;
  - this service expects a secret called `db_password`.
- `secrets` section:
  - the secrets section defines `db_password`;
  - the line `external: true` means that this secret is not created by Docker Compose but instead [already exists](../create-secret/create_secret.md) in the Docker Swarm secret store.

**`docker-compose.yml` file:**

```yaml
version: "3.7"
services:
  app:
    image: my_app
    secrets:
      - db_password
secrets:
  db_password:
    external: true
```
