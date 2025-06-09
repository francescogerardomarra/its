# Comparison

In this chapter, we show a comparison between the bind mounts, named volumes and anonymous volumes.

**Table:**

| **Feature**           | **Bind mounts**                                                                                           | **Named volumes**                                                                     | **Anonymous volumes**                                                             |
|-----------------------|-----------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| **Definition**        | gives access to a file or directory on the host system.                                                   | managed by Docker and stored in a part of the host filesystem controlled by Docker.   | created by Docker without a specific name and managed automatically.              |
| **Setup**             | - defined with full path on the host;<br>- specified in the `docker run` command or `docker-compose.yml`. | - defined with a name;<br>- can be reused across containers.                          | - not given a name by the user;<br>- hard to reference explicitly.                |
| **Use case**          | - ideal for development when files need to be synchronized;<br>- useful when sharing host data.           | - ideal for persisting data between container restarts;<br>- good for production use. | - suitable for ephemeral containers;<br>- used when persistence is not a concern. |
| **Data persistence**  | persists as long as the host path exists.                                                                 | persists even if no container is using it.                                            | persists even if no container is using it.                                        |
| **Docker management** | not managed by Docker.                                                                                    | fully managed by Docker.                                                              | managed by Docker but not explicitly controlled by user.                          |
| **Backup/restore**    | - manual backup and restore needed;<br>- tools external to Docker are typically used.                     | - easy to back up using Docker CLI;<br>- can be managed using Docker volume commands. | - hard to identify and back up;<br>- usually ignored for backup purposes.         |
| **Portability**       | not portable due to absolute host path dependencies.                                                      | portable across environments via named reference.                                     | not portable and hard to trace.                                                   |
