# How `healthcheck` Is Used After Startup

- in this example, we are referring to the healthcheck of [db](../healthcheck/healthcheck.md) service;
- if a service defines a `healthcheck:`:
    - Docker will monitor and update its health status (visible via `docker ps`);
    - however, this health status has **no impact** on Compose behavior unless it's used in another service's `depends_on` with `condition: service_healthy`;
- Docker Compose will only use health status at **startup** if configured to wait for a service to become healthy;


- after all services are started, Docker Compose will not respond to changes in health, even though health checks continue to run.
