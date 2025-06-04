# `docker compose stop`

- this command is the same of previous [docker compose down](../compose-down/compose_down.md) command, **but it doesn't remove the containers and the networks**;
- it just stops the service containers;
- if you don't change important configurations, (e.g., the containers port mapping like `4000:3000`) the next `docker compose up` will reuse the stopped containers instead of creating new ones;


- it's possible to select which service to stop (**default is all**):

    ```commandline
    docker compose stop <service_name_1> <service_name_2> [<another_service> ...]
    ```
- default signal to stop containers is `SIGTERM` (**it's not possible to change it**):
  - after a grace period (default: **10 seconds**), if the container hasn’t exited, Docker sends `SIGKILL` to force it to stop.