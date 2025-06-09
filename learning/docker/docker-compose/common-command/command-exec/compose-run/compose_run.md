# `docker compose run`

- this command is the same of [docker compose exec](../compose-exec/compose_exec.md), but instead of running the command within the already running service container, it creates a new one;
- it's good for one-off task;
- it does not require the service to be running beforehand;


- it runs independently of any `docker compose up` containers;
- the container is temporary, once the command finishes, the container usually stops and goes away.

**Example:**

1. run the [docker compose exec](../compose-exec/compose_exec.md) example bash;
2. open another terminal and run:

    ```commandline
    docker ps
    ```
    
    output:
    
    ```commandline
    6a874e900c4f   java-program:1.0       "java Hello"             45 minutes ago      Up 45 minutes                                            hello-world-my-service-1
    ```

    - as you can see just one container instantiated from `java-program:1.0` is running;
    - since the `docker compose exec` command is running within it.

3. exit the bash and run this command within the project root folder:

    ```commandline
    docker compose run my-service bash
    ```

4. open another terminal and run:

    ```commandline
    docker ps
    ```
    
    output:
    
    ```commandline
    c2c6c0282539   java-program:1.0       "bash"                   12 seconds ago      Up 12 seconds                                            hello-world-my-service-run-a0c03a827c4b
    6a874e900c4f   java-program:1.0       "java Hello"             45 minutes ago      Up 45 minutes                                            hello-world-my-service-1
    ```
    
    - now 2 containers instantiated from `java-program:1.0` are running;
    - the `c2c6c0282539` is the temporary one, where `docker compose run` command is running;
    - if you exit the bash, this container will be terminated.
