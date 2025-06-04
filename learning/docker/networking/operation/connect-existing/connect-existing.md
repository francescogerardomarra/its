# Connect Existing Container to Network

It's possible to attach an existing container to an existing network (**container can be running or stopped**):

1. create a custom network `my-custom-network`:

    ```commandline
    docker network create my-custom-network
    ```

2. create an Nginx container:

    ```commandline
    docker run -d --name my-nginx-container nginx
    ```

3. attach `my-nginx-container` to `my-custom-network`:

    ```commandline
    docker network connect my-custom-network my-nginx-container
    ```
4. check if now `my-nginx-container` is connected to `my-custom-network`:

    ```commandline
    docker network inspect my-custom-network
    ```
    
    output:
    
    ```commandline
    [
        {
            "Name": "my-custom-network",
            "Id": "2637056b2b35f7ed5574704411e201ff0c52ebde8ef5392a2f926c4a786a5787",
            "Created": "2025-04-24T12:15:25.93664339+02:00",
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
                "fe3166dd6650706e12d76966d45b5ae77fbc4a482c13378bcfb9b2a564633e06": {
                    "Name": "my-nginx-container",
                    "EndpointID": "6fefce6c2e53a0df1e950c990a03da27f7237c5354d407075e891ee1659bb9e2",
                    "MacAddress": "a6:a7:b5:85:c9:4c",
                    "IPv4Address": "172.18.0.2/16",
                    "IPv6Address": ""
                }
            },
            "Options": {},
            "Labels": {}
        }
    ]
    ```
