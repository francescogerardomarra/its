# Ping Command Explanation 

- in this chapter, we explain the ping command of the [previous](../definition/definition.md) example;
- command:

  ```yaml
  command: ["sh", "-c", "ping db -c 3"]
  ```
- **`sh`**: 
  - this is the shell program (like Bash) that will be used to execute the command;
  - BusyBox, which is a lightweight Linux distribution often used in Docker containers, uses `sh` as its default shell.
- **`-c`**: this tells the shell to execute the following command string;
- **`ping db -c 3`**:
    - `ping`: sends ICMP echo request packets to test connectivity;
    - `db`: 
      - the hostname of the service you are trying to reach;
      - in this context, it refers to the `db` service defined in the same Docker Compose file.
    - `-c 3`: limits the number of ping attempts to 3, after which the command will exit.
