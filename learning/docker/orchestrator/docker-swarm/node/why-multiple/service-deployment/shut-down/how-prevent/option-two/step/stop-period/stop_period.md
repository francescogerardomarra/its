# `stop_grace_period` Option

- you can configure how long Docker Swarm will wait after sending `SIGTERM` before forcefully terminating the container with `SIGKILL` (the default wait time is 10 seconds);
- this tells Swarm how long to wait for the container to shut down gracefully before killing it:

    ```commandline
    docker service update --stop-grace-period 30s your_service_name
    ```
