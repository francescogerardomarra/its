# Check if the Service is Running

- check if the service is running with the command:
    
    ```commandline
    docker service ls
    ```

    **output:**

    ```commandline
    ID             NAME            MODE         REPLICAS   IMAGE              PORTS
    kdp9t6525jmm   swarm-service   replicated   1/1        secret-swarm:1.0   
    ```

- if you want to stop the service without deleting it, set the replicas to zero:

    ```commandline
    docker service scale <service_name>=0
    ```  

- if you want to stop and remove the service:

    ```commandline
    docker service rm <service_name>
    ```
