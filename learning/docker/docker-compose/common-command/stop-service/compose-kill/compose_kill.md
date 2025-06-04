# `docker compose kill`

- similar to [docker compose stop](../compose-stop/compose_stop.md), but the signal sent to stop containers is different;
- it sends `SIGKILL` immediately by default (no delay);
- the container is terminated instantly;


- no cleanup, no grace period;
- it allows to select which stop signal send:

    ```commandline
    docker compose kill --signal SIGTERM <service_name>
    ```
