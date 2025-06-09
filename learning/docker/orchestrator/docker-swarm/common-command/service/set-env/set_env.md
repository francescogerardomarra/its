# Set Some Environment Variables

**CLI:**

Open a terminal and launch this command:

```commandline
docker service create \
  --name myapp \
  --env ENV_MODE=production \
  --env DEBUG=false \
  nginx
```

**`docker-compose.yml` file:**

See this [example](../../../../../docker-compose/component/service/example/compose-file/compose_file.md) to learn how to define environment variables in a `docker-compose.yml` file when deploying a service with Docker Swarm using Docker Compose.
