# `docker-compose.yml` file

- Docker Compose supports defining environment variables in `docker-compose.yml`;
- variables can be set directly under the `environment` section;
- the `env_file` option loads variables from a `.env` file;


- this keeps configurations organized and easy to manage;
- the `.env` file must be in the same directory as `docker-compose.yml` or its full path must be specified in the `env_file` section.

**Example `docker-compose.yml` file:**

```yaml
version: "3"
services:
  app:
    image: my_image
    environment:
      - DB_HOST=localhost
      - DB_USER=root
    env_file:
      - .env
```
