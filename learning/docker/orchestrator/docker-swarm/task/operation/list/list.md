# List Tasks

1. list all the services:

    ```commandline
    docker service ls
    ```
    
    output:
    
    ```commandline
    ID             NAME              MODE         REPLICAS   IMAGE          PORTS
    wgvr42ewrjqu   my-service        replicated   1/1        nginx:latest   
    6bxomvzmaq7b   myservice-redis   replicated   1/1        redis:latest   
    9x01yzul6njo   myweb             replicated   3/3        nginx:latest  
    ```

2. list all the tasks of a service (`myweb` in our case):

    ```commandline
    docker service ps myweb
    ```
    
    output:
    
    ```commandline
    ID             NAME          IMAGE          NODE        DESIRED STATE   CURRENT STATE          ERROR                              PORTS
    lw44jk515eve   myweb.1       nginx:latest   avolpe-pc   Running         Running 3 hours ago                                       
    nw4rvwu2nslj    \_ myweb.1   nginx:latest   avolpe-pc   Shutdown        Failed 3 hours ago     "No such container: myweb.1.nw…"   
    ou9yaydxf6i9    \_ myweb.1   nginx:latest   avolpe-pc   Shutdown        Shutdown 3 hours ago                                      
    te7tr78vtzsj   myweb.2       nginx:latest   avolpe-pc   Running         Running 3 hours ago                                       
    zn2dfmhc6tcm    \_ myweb.2   nginx:latest   avolpe-pc   Shutdown        Failed 3 hours ago     "No such container: myweb.2.zn…"   
    j0ukynqspasx    \_ myweb.2   nginx:latest   avolpe-pc   Shutdown        Shutdown 3 hours ago                                      
    l88l888d4lxf   myweb.3       nginx:latest   avolpe-pc   Running         Running 3 hours ago                                       
    o49fwm8s7es3    \_ myweb.3   nginx:latest   avolpe-pc   Shutdown        Failed 3 hours ago     "No such container: myweb.3.o4…"   
    djby3vmnkkwa    \_ myweb.3   nginx:latest   avolpe-pc   Shutdown        Shutdown 3 hours ago    
    ```

    - each task has a revision history; 
    - newer tasks replace older failed ones;
    - tasks fail if Swarm can’t start them correctly, often due to image pull issues, container runtime errors, or external dependencies.

3. list all the task of another service (`myservice-redis` in our case):

    ```commandline
    docker service ps myservice-redis
    ```
    
    output:
    
    ```commandline
    ID             NAME                    IMAGE          NODE        DESIRED STATE   CURRENT STATE         ERROR                              PORTS
    uatg1zylcdgn   myservice-redis.1       redis:latest   avolpe-pc   Running         Running 3 hours ago                                      
    xf0tjuzexbi3    \_ myservice-redis.1   redis:latest   avolpe-pc   Shutdown        Failed 3 hours ago    "No such container: myservice-…"   
    ```
