# Display Container Logs Static Dynamic

- the following command is the dynamic version of the [previous](../static/static.md) one;
- the `-f` flag stands for **follow**;
- this command displays the logs of the container and keeps following them in real-time, showing new logs as they are generated:

    ```bash
    docker logs -f <container_name|container_id>
    ```

- executed command:

    ```bash
    docker logs -f 46fbfb5d5e8d
    ```

- output:

    ```bash
    AH00558: httpd: Could not reliably determine the server's fully qualified domain name, using 172.17.0.3. Set the 'ServerName' directive globally to suppress this message
    AH00558: httpd: Could not reliably determine the server's fully qualified domain name, using 172.17.0.3. Set the 'ServerName' directive globally to suppress this message
    [Thu Nov 28 09:37:56.228274 2024] [mpm_event:notice] [pid 1:tid 1] AH00489: Apache/2.4.62 (Unix) configured -- resuming normal operations
    [Thu Nov 28 09:37:56.228364 2024] [core:notice] [pid 1:tid 1] AH00094: Command line: 'httpd -D FOREGROUND'
    [Thu Nov 28 09:38:02.892086 2024] [mpm_event:notice] [pid 1:tid 1] AH00491: caught SIGTERM, shutting down
    AH00558: httpd: Could not reliably determine the server's fully qualified domain name, using 172.17.0.4. Set the 'ServerName' directive globally to suppress this message
    AH00558: httpd: Could not reliably determine the server's fully qualified domain name, using 172.17.0.4. Set the 'ServerName' directive globally to suppress this message
    [Thu Nov 28 09:44:17.849624 2024] [mpm_event:notice] [pid 1:tid 1] AH00489: Apache/2.4.62 (Unix) configured -- resuming normal operations
    [Thu Nov 28 09:44:17.849718 2024] [core:notice] [pid 1:tid 1] AH00094: Command line: 'httpd -D FOREGROUND'
    ```

- note: the difference is in the output: 
  - in this version, the output console remains open until you forcibly close it and prints all the logs in real time;
  - in the [previous](../static/static.md) version, the output console displays all logs produced so far by the specified container.
