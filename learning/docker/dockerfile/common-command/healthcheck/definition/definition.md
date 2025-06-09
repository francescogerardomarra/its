# `HEALTHCHECK` Definition

- **purpose:**
    - defines a command that Docker runs periodically to check if the container is still healthy;
    - helps detect issues inside a running container and can mark it as **healthy** or **unhealthy**;
    - if a container becomes **unhealthy**, Docker can take actions like restarting it (if the `docker run` command uses `--restart` flag).

- **syntax:**

  ```dockerfile
  HEALTHCHECK [OPTIONS] CMD <command>
  ```
- **options:**
    - `--interval=<duration>` = time between health checks (default: 30s);
    - `--timeout=<duration>` = max time the health check can run (default: 30s);
    - `--retries=<number>` = number of failures before marking as unhealthy (default: 3);
    - `--start-period=<duration>` = time to wait before starting checks after container startup.
