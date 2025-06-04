# Using Docker Compose

- open the terminal within the project root folder and run this command (to see the logs of `app` service):

    ```commandline
    docker compose logs app
    ```

- output:

  ```commandline
  my_app  | Hello World 1
  my_app  | Hello World 2
  my_app  | Hello World 3
  my_app  | Hello World 4
  my_app  | Hello World 5
  ```

**In case of multiple replicas of `app` service:**

- the `docker compose logs app` command will display logs from all replicas of the "app" service simultaneously;
- each log entry will be prefixed with the name of the container, which typically includes the service name, replica index, and an auto-generated suffix (e.g., `app_1`, `app_2`, `app_3`);
- the log messages from all replicas will be interleaved, as Docker does not guarantee the order of log messages from different containers;


- each log message will contain the `Hello World` string;
- since each replica prints `Hello World` every 10 seconds, you will see a continuous stream of `Hello World` messages appearing at slightly different times, depending on the replica's timing and startup sequence.
