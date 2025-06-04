# Inspect Networks

- run this command to see all the network Docker is using:

    ```commandline
    docker network ls
    ```

    output:

    ```commandline
    NETWORK ID     NAME                      DRIVER    SCOPE
    427ac6ece954   bridge                    bridge    local
    121bb0e0e8d3   compose-example_default   bridge    local
    46c8f553c08f   docker_gwbridge           bridge    local
    02aa7e0012e4   host                      host      local
    jfmrunhxckb5   ingress                   overlay   swarm
    344e8832134f   my-custom-network         bridge    local
    64540c89c743   my-net                    bridge    local
    z4yqebb802zb   mystack_default           overlay   swarm
    de787df8f4a8   none                      null      local
    ```
  
- scope:
   - local: the network exists only on the local Docker host;
   - swarm: the network is part of a Docker Swarm cluster, spanning multiple nodes.

- inspect a specific network:

    ```commandline
    docker network inspect my-custom-network
    ```
    
    output:
    
    ```commandline
    [
        {
            "Name": "my-custom-network",
            "Id": "344e8832134f7bdaf87da8af4fa5a1527aef902f09ce1d9886da9a0f9d88fd98",
            "Created": "2025-04-24T10:32:43.349244732+02:00",
            "Scope": "local",
            "Driver": "bridge",
            "EnableIPv4": true,
            "EnableIPv6": false,
            "IPAM": {
                "Driver": "default",
                "Options": {},
                "Config": [
                    {
                        "Subnet": "172.18.0.0/16",
                        "Gateway": "172.18.0.1"
                    }
                ]
            },
            "Internal": false,
            "Attachable": false,
            "Ingress": false,
            "ConfigFrom": {
                "Network": ""
            },
            "ConfigOnly": false,
            "Containers": {
                "080bc947bc3ea7f44bf98dde8ad04374ac8d907dc0a95e918992cf945c720105": {
                    "Name": "my-custom-container",
                    "EndpointID": "34c23d97103386f9873599b9c665baaf9a21f410ba9de325ddc576e4af38f3ec",
                    "MacAddress": "12:57:00:de:e3:7c",
                    "IPv4Address": "172.18.0.2/16",
                    "IPv6Address": ""
                }
            },
            "Options": {},
            "Labels": {}
        }
    ]
    ```

    `Containers` section, shows the containers connected to that network.
