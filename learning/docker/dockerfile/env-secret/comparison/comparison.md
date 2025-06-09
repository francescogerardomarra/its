# Comparison

- in this chapter, we show a comparison between using env variable and using secrets for sensitive data in a `Dockerfile`;
- the comparison of using env variables and secrets for sensitive data is explained also in the chapter [here](../secret/why-use/why_use.md).

**Table:**

| **Aspect**                   | **Environment variables**                                                                                                                   | **Docker secrets**                                                                                                                         |
|------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|
| **Security**                 | - values are visible in image layers if defined in Dockerfile; <br> - accessible through `docker inspect`; <br> - not encrypted by default. | - data is mounted as files and not exposed via environment; <br> - access is limited to containers that need it; <br> - encrypted at rest. |
| **Ease of use**              | - simple to implement with `ENV` or `--env`; <br> - widely supported across Docker tools.                                                   | - requires Docker Swarm or Kubernetes; <br> - additional configuration is necessary.                                                       |
| **Access scope**             | - available to all processes within the container; <br> - inherited by child processes.                                                     | - only accessible within the container's file system at a mounted location; <br> - not passed to child processes.                          |
| **Persistence**              | - persists across restarts if defined in image or `docker-compose`; <br> - does not depend on volumes.                                      | - persists only while the service is running; <br> - removed after container stops (if not stored in a volume).                            |
| **Best use case**            | - suitable for less sensitive configuration data.                                                                                           | - recommended for managing sensitive credentials like API keys and passwords.                                                              |
| **Visibility in logs**       | - may be exposed in logs or error messages.                                                                                                 | - not exposed in logs or standard output.                                                                                                  |
| **Dockerfile compatibility** | - can be directly defined using `ENV` instruction.                                                                                          | - cannot be used directly in Dockerfile; must be mounted at runtime.                                                                       |
