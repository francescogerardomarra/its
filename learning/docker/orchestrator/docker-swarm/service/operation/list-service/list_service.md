# List Services

- run this command on a manager node:

    ```commandline
    docker service ls
    ```

- output:

    ```commandline
    ID             NAME              MODE         REPLICAS   IMAGE          PORTS
    6bxomvzmaq7b   myservice-redis   replicated   1/1        redis:latest   
    9x01yzul6njo   myweb             replicated   3/3        nginx:lates
    ```
