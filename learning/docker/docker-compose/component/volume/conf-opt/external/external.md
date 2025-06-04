# External

- external volumes in Docker Compose allow you to reference pre-existing Docker volumes instead of creating new ones;
- they are useful when you want multiple services or stacks to share the same data, or when you need to persist data across container and stack redeployments;
- to define an external volume, you specify it under the `volumes` section in the `docker-compose.yml` file with the `external: true` option;


- the volume name used in the `docker-compose.yml` file must exactly match the name of the pre-existing Docker volume;
- if the specified volume does not exist, Docker Compose will throw an error instead of creating a new one;
- external volumes are often used in production environments to ensure that data is retained even if containers are stopped or removed;


- external volumes are usually created manually using the `docker volume create` command or by other orchestration tools outside of the Docker Compose file;
- this approach ensures that the volume is managed separately from the application stack, allowing for greater control and persistence of critical data.

**Example:**

```yaml
version: '3.9'

services:
  myapp:
    image: nginx:latest
    ports:
      - "8080:80"
    volumes:
      - mydata:/usr/share/nginx/html

volumes:
  mydata:
    external: true
```
