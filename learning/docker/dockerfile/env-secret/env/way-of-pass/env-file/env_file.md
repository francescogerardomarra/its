# `.env` file

- a `.env` file stores environment variables as key-value pairs;
- it helps manage multiple environment variables in one place;
- example format: `DB_HOST=localhost`, `DB_USER=root`, `DB_PASS=secret`;


- use `docker run --env-file .env my_image` to load variables in a container;
- using a file is easier and more scalable than passing variables with [`-e` flag](../docker-run/docker_run.md);
- it allows easy updates and reuse across multiple containers without modifying commands.

**Example `.env` file:**

```ini
DB_HOST=localhost
DB_USER=root
DB_PASS=secret
```

**Run the container using:**

```commandline
docker run --env-file .env my_image
```
