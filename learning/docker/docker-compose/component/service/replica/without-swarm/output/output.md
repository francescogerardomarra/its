# Output

- [previous](../example/example.md) command:

    ```commandline
    docker compose up --scale app=3 --scale db=2 --scale redis=1 -d
    ```
    
    output:
    
    ```commandline
    [+] Running 7/7
     ✔ Network compose-example_default    Created                                                                                                                                                        0.1s 
     ✔ Container compose-example-redis-1  Started                                                                                                                                                        0.2s 
     ✔ Container compose-example-db-2     Started                                                                                                                                                        0.2s 
     ✔ Container compose-example-db-1     Started                                                                                                                                                        0.4s 
     ✔ Container compose-example-app-3    Started                                                                                                                                                        0.8s 
     ✔ Container compose-example-app-1    Started                                                                                                                                                        0.5s 
     ✔ Container compose-example-app-2    Started  
    ```

- check the running services:

    ```commandline
    docker compose ps
    ```
    
    output:
    
    ```commandline
    NAME                      IMAGE                 COMMAND                  SERVICE   CREATED          STATUS          PORTS
    compose-example-app-1     compose-example-app   "java -jar compose-e…"   app       13 seconds ago   Up 12 seconds   0.0.0.0:32772->5000/tcp
    compose-example-app-2     compose-example-app   "java -jar compose-e…"   app       13 seconds ago   Up 12 seconds   0.0.0.0:32773->5000/tcp
    compose-example-app-3     compose-example-app   "java -jar compose-e…"   app       13 seconds ago   Up 12 seconds   0.0.0.0:32774->5000/tcp
    compose-example-db-1      mysql:latest          "docker-entrypoint.s…"   db        13 seconds ago   Up 13 seconds   33060/tcp, 0.0.0.0:32771->3306/tcp
    compose-example-db-2      mysql:latest          "docker-entrypoint.s…"   db        13 seconds ago   Up 13 seconds   33060/tcp, 0.0.0.0:32770->3306/tcp
    compose-example-redis-1   redis:latest          "docker-entrypoint.s…"   redis     13 seconds ago   Up 13 seconds   0.0.0.0:6379->6379/tcp
    ```
