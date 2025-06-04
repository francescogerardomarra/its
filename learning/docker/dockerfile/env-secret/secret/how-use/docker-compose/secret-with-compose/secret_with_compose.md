# Create the Secret Within the `docker-compose.yml` File

- if you want Docker Swarm to create the secret for you when deploying the stack, you should not use [external: true](../create-compose/create_compose.md);
- with the following command, Docker Swarm will create the `db_password` secret automatically from the contents of `db_password.txt` file when you run the command to [deploy](../deploy-stack/deploy_stack.md) the stack;
- in this case, no need to manually create the secret with [docker secret create](../create-secret/create_secret.md) command.

**docker-compose.yml file:**

```yaml
version: "3.7"
services:
  app:
    image: my_app
    secrets:
      - db_password
secrets:
  db_password:
    file: ./db_password.txt  # Swarm will create the secret from this file
```
