# Remove a Running Container

- this command is used to stop a running container and remove it in one step (without this flag, you cannot remove a container that is currently running):

   ```bash
   docker rm -f <container_name|container_id>
   ```

- executed commands:

    ```bash
    docker rm -f 178cabd7a81e
    ```

- output:

    ```bash
    178cabd7a81e
    ```

- list all containers:

    ```bash
    5ebfbb3ec809   httpd                                        "httpd-foreground"       35 minutes ago   Exited (0) 35 minutes ago                                         festive_vaughan
    46fbfb5d5e8d   httpd                                        "httpd-foreground"       42 minutes ago   Up 35 minutes                80/tcp                               dreamy_matsumoto
    45f6798e8c9b   httpd                                        "httpd-foreground"       42 minutes ago   Up 35 minutes                80/tcp                               pedantic_payne
    5b21be3df384   postgres                                     "docker-entrypoint.s…"   43 minutes ago   Exited (1) 15 minutes ago                                         hungry_ptolemy
    2319a5139823   httpd:2.4                                    "httpd-foreground"       7 days ago       Exited (0) 7 days ago                                             my_site
    c1610bb52e36   postgres                                     "docker-entrypoint.s…"   8 days ago       Exited (0) 7 days ago                                             new-db
    ```
