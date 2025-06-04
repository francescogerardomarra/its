# `docker network ls`

- this command lists all networks on your machine:

    ```commandline
    docker network ls
    ```

- output:

    ```commandline
    NETWORK ID     NAME                      DRIVER    SCOPE
    427ac6ece954   bridge                    bridge    local
    121bb0e0e8d3   compose-example_default   bridge    local
    46c8f553c08f   docker_gwbridge           bridge    local
    02aa7e0012e4   host                      host      local
    jfmrunhxckb5   ingress                   overlay   swarm
    2637056b2b35   my-custom-network         bridge    local
    64540c89c743   my-net                    bridge    local
    z4yqebb802zb   mystack_default           overlay   swarm
    de787df8f4a8   none                      null      local
    ```

- scope:
    - local: the network exists only on the local Docker host;
    - swarm: the network is part of a Docker Swarm cluster, spanning multiple nodes.
