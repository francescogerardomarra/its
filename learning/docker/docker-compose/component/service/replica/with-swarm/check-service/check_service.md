# Check If Stack and Services are Running

1. check if the stack is running:

   ```commandline
   docker stack ls
   ```

   output:

   ```commandline
   NAME                    SERVICES
   compose-example-stack   3
   ```

2. check if services are running:

   ```commandline
   docker service ls
   ```

   output:

   ```commandline
   ID             NAME                          MODE         REPLICAS   IMAGE                       PORTS
   w9yaxc9qu33h   compose-example-stack_app     replicated   3/3        compose-example-image:1.0   *:5000->5000/tcp
   9dhr0xw3qcro   compose-example-stack_db      replicated   2/2        mysql:latest                *:3306->3306/tcp
   jai5tu3nc5y3   compose-example-stack_redis   replicated   1/1        redis:latest                *:6379->6379/tcp
   ```
