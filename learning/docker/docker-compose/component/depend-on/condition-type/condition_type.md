# Type of `condition`

- in this chapter, we see all the [condition](../definition/definition.md) types available in Docker Compose;
- "Supported in Compose Version" column refers to the Compose file format version specified in the version:
  - field of a `docker-compose.yml` file (e.g., version: "3.9");
  - this determines which features and syntax are available in the Compose file;
  - it is not the same as the Docker Compose CLI version, which refers to the installed version of the Docker Compose tool itself (e.g., v2.24.0) and can support multiple Compose file format versions.

**Table:**

| Condition                        | Meaning                                                                   | Supported in Compose Version |
|----------------------------------|---------------------------------------------------------------------------|------------------------------|
| `service_started`                | the service's container has been created and started (default in v2.x).   | 2.x only                     |
| `service_healthy`                | the service is marked as `healthy` via its `healthcheck`.                 | 2.x, 3.4+                    |
| `service_completed_successfully` | the service ran, exited, and returned exit code `0` (for one-shot tasks). | 2.x only                     |
