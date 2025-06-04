# Inspect

In this example, we inspect the `desktop_custom_network_2` set within the [previous](../check/check.md) example.

- inspect `desktop_custom_network_2`:

    ```commandline
    docker network inspect desktop_custom_network_2
    ```

   output:

    ```json
    [
        {
            "Name": "desktop_custom_network_2",
            "Id": "f673103cf1e3463fb603aaf758402dac07f9a7d3ee759905c98111202f7b0dba",
            "Created": "2025-03-25T11:53:35.977976789+01:00",
            "Scope": "local",
            "Driver": "bridge",
            "EnableIPv4": true,
            "EnableIPv6": false,
            "IPAM": {
                "Driver": "default",
                "Options": null,
                "Config": [
                    {
                        "Subnet": "172.24.0.0/16",
                        "Gateway": "172.24.0.1"
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
                "7790527603bc3368ea46343ca0e550fdeb0033dd7985e065a93ffe28340eb68e": {
                    "Name": "service2",
                    "EndpointID": "fce80be76cdd646e5288726f96d82da3a24ea64ccf94a2e9d27355bc8e0efbb9",
                    "MacAddress": "b2:a0:57:e4:5e:87",
                    "IPv4Address": "172.24.0.2/16",
                    "IPv6Address": ""
                }
            },
            "Options": {},
            "Labels": {
                "com.docker.compose.config-hash": "ef70edfe60e3546eebab9b31a29100e7a9625f2b2724c3417bdbf9afb5406539",
                "com.docker.compose.network": "custom_network_2",
                "com.docker.compose.project": "desktop",
                "com.docker.compose.version": "2.34.0"
            }
        }
    ]
    ```

- from this output we can see all the properties of the network, including:
    - the containers connected to it;
    - many configurations set in [before](../../../../../index.md) chapters.

