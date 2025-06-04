# Example

- **check if a web server inside the container is running:**

  ```dockerfile
  HEALTHCHECK --interval=30s --timeout=10s --retries=3 CMD curl -f http://localhost || exit 1
  ```
    - `curl -f http://localhost` = tries to access the web service;
    - `|| exit 1` = if `curl` fails (e.g., the service is down), the command **exits with status 1**, marking the container as **unhealthy**;
    - by default, if a command fails (like curl failing to reach a web service), it may not always return a non-zero exit code;
    - adding `|| exit 1` ensures that a failure is properly reported as unhealthy;
    - **it's common to expose a simple get endpoint dedicated to health check when building a web service.**
- **use a custom script to check health status:**

  ```dockerfile
  COPY healthcheck.sh /usr/local/bin/
  HEALTHCHECK CMD ["sh", "/usr/local/bin/healthcheck.sh"]
  ```

- **disable health checks (if not needed):**

  ```dockerfile
  HEALTHCHECK NONE
  ```

- **restart the container automatically if it becomes unhealthy:**

  ```sh
  docker run --restart=always my-container
  ```
    - this ensures that if the container is marked **unhealthy**, Docker will attempt to restart it;
    - **without `HEALTHCHECK`**, `--restart=always` will only restart the container if it **exits unexpectedly** (e.g., crashes), but it won’t detect if the application inside is failing;
    - **without `--restart=always`**, `HEALTHCHECK` can still mark the container as unhealthy, but Docker won’t restart it automatically;
    - **combining both `HEALTHCHECK` and `--restart=always`** ensures that the container **recovers from both crashes and internal failures**.
