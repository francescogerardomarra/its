# Check Resource Usage (CPU, memory, I/O)

- the following command is used to display live, real-time resource usage statistics for your running Docker containers;
- it provides insight into how much CPU, memory, network, and disk I/O each container is consuming.

  ```bash
  docker stats [OPTIONS] [CONTAINER...]
  ```

- executed command:

  ```bash
  docker stats --no-stream 8d229284248f
  ```

- command overview:
  - the `--no-stream` flag shows a one-time snapshot instead of continuously updating the stats

- output:

  ```bash
  CONTAINER ID   NAME             CPU %     MEM USAGE / LIMIT     MEM %     NET I/O   BLOCK I/O     PIDS
  8d229284248f   elated_meitner   0.55%     18.53MiB / 38.96GiB   0.05%     0B / 0B   42.8MB / 0B   6
  ```

- list running containers:

  ```bash
  CONTAINER ID   IMAGE        COMMAND                  CREATED         STATUS             PORTS                                NAMES
  8d229284248f   redis        "docker-entrypoint.s…"   19 hours ago    Up 4 minutes       6379/tcp                             elated_meitner
  76799c447caf   ubuntu       "/bin/bash"              20 hours ago    Up 4 minutes                                            nervous_agnesi
  141a7e6f3bae   nginx        "/docker-entrypoint.…"   20 hours ago    Up 4 minutes       80/tcp                               cool_hugle
  3dc0044aed4b   registry:2   "/entrypoint.sh /etc…"   16 months ago   Up About an hour   5000/tcp, 0.0.0.0:32000->32000/tcp   registry
  ```
