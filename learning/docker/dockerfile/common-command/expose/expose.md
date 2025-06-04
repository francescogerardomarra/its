# EXPOSE

- **purpose:**
  - declares that the container **intends** to listen on a specific network port at runtime;
  - does **not** automatically publish or open the port to the host; it only serves as documentation;
  - to access the exposed port from outside, you must use the `-p` or `-P` flag in `docker run`.

- **syntax:**

    ```dockerfile
    EXPOSE <port> [<protocol>]
    ```
    
    (default protocol is `TCP`, but you can specify `UDP` if needed).

- **example:**
  - expose port 80 (default protocol is TCP):
  
    ```dockerfile
    EXPOSE 80
    ```
  - expose multiple ports:
  
    ```dockerfile
    EXPOSE 8080 443
    ```
  - expose a UDP port explicitly:
  
    ```dockerfile
    EXPOSE 53/udp
    ```
  - to make the exposed ports accessible, run the container with (see more details [here](../../../container/common-command/creation-execution/run/port-mapping/port_mapping.md)):

      ```sh
      docker run -p 8080:8080 my-container
      ```
