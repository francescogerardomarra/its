# `docker compose rm`

- this command is used to remove stopped services/containers that were created by a Docker Compose setup;
- it removes stopped containers created by [docker compose up](../../create-start-service/compose-up/compose_up.md);
- it removes containers (not volumes or networks, unless specified);


- it only affects containers that are stopped, it won’t stop any running ones;
- it asks for confirmation before removing, unless you add flags;
- usually you run this command after [docker compose stop](../../stop-service/compose-stop/compose_stop.md) command.
