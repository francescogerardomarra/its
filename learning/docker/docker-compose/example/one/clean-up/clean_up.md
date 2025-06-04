# Clean Up

In this chapter, we see how to completely remove the Docker Compose stack and the associated networks and containers:

1. open a terminal and run this command to discover the stack name:

    ```commandline
    docker stack ls
    ```

    output:

    ```commandline
    NAME                             SERVICES
    complete-compose-example-stack   3
    ```
2. remove the stack:

    ```commandline
    docker stack rm complete-compose-example-stack 
    ```
