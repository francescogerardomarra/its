# `healthcheck`

- see the `depends_on` with `condition:` on `web` service [here](../definition/definition.md);
- the `db` service must define a `healthcheck` — a script that tells Docker if the service is ready:

    ```yaml
    version: "3.9"
    
    services:
      web:
        # web setup here ...
            
    db:
      image: postgres
      healthcheck:
        test: ["CMD", "pg_isready", "-U", "postgres"]
        interval: 10s
        timeout: 5s
        retries: 5
    ```

**Above example explanation:**

- this runs `pg_isready` every 10 seconds, up to 5 tries;
- if PostgreSQL is accepting connections, the container is marked as **healthy**;
- if you try to use `condition: service_healthy` without defining a `healthcheck`, Docker Compose will throw an error;


- there is **no default healthcheck**, you must provide one if you expect Docker to wait.

**What actually happens:**

1. Compose starts the `db` container;
2. Docker runs your `healthcheck`;
3. once `db` is marked healthy, `web` is allowed to start;
4. after that, `web` is on its own, Compose won't babysit ongoing health.
