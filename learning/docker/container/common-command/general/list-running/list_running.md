# List Running Containers

**Just Running Containers:**

- this command lists only the **running containers**;
- it shows details such as container ID, image name, command, status, ports, and names of the running containers:

   ```bash
   docker ps
   ```
  
- output:

    ```bash
    CONTAINER ID   IMAGE        COMMAND                  CREATED          STATUS          PORTS                                NAMES
    178cabd7a81e   httpd        "httpd-foreground"       19 seconds ago   Up 18 seconds   80/tcp                               sleepy_shamir
    3dc0044aed4b   registry:2   "/entrypoint.sh /etc…"   16 months ago    Up 42 minutes   5000/tcp, 0.0.0.0:32000->32000/tcp   registry
    ```

**Include Stopped Containers:**

- this command lists all containers, including both running and stopped containers;
- it provides the same details as docker ps, but also includes containers that have exited or were previously stopped:

   ```bash
   docker ps -a
   ```

- output:

    ```bash
    CONTAINER ID   IMAGE                                        COMMAND                  CREATED              STATUS                          PORTS                                NAMES
    178cabd7a81e   httpd                                        "httpd-foreground"       51 seconds ago       Up 50 seconds                   80/tcp                               sleepy_shamir
    46fbfb5d5e8d   httpd                                        "httpd-foreground"       About a minute ago   Exited (0) 58 seconds ago                                            dreamy_matsumoto
    45f6798e8c9b   httpd                                        "httpd-foreground"       About a minute ago   Exited (0) About a minute ago                                        pedantic_payne
    dc1fc7b84b53   httpd                                        "httpd-foreground"       About a minute ago   Exited (0) About a minute ago                                        unruffled_pare
    5b21be3df384   postgres                                     "docker-entrypoint.s…"   2 minutes ago        Exited (1) 2 minutes ago                                             hungry_ptolemy
    ```
