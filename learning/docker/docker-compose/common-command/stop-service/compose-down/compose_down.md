# `docker compose down`

- stop containers that were started with [docker compose up](../../create-start-service/compose-up/compose_up.md);
- removes containers (so they're not just stopped, they're deleted);
- removes networks created by the Compose file (unless they are external networks);


- optionally removes volumes if you specify the `--volumes` flag;
- optionally removes images if you use the `--rmi` flag;
- it must be run in the same directory where your `docker-compose.yml` file is located.

**Example:**

- `docker-compose.yml` file:

    ```commandline
    services:
      web:
        build: .
        image: myapp:1.0
    ```

- command to run:

    ```commandline
    docker compose down
    ```

- outcome:
    - it will remove the web service container;
    - it will remove the default bridge network created;
    - it will not remove the `myapp:1.0` image from your machine.
