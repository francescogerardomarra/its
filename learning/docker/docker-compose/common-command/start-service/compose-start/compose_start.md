# `docker compose start`

- this command is used to start existing containers that were previously created and stopped using Docker Compose;
- it does not create containers;
- it does not rebuild images;


- it does not pull new images;
- it only starts containers that were already created with [docker compose up](../../create-start-service/compose-up/compose_up.md) or [docker compose create](../../create-service/compose-create/compose_create.md) and stopped with [docker compose stop](../../stop-service/compose-stop/compose_stop.md) command.
