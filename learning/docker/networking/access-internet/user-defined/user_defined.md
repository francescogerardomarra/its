# User-Defined Network

- you run containers and attach them to your custom network:

    ```commandline
    docker run --network my-network alpine ping google.com
    ```

- the container gets a private IP in that network’s subnet;
- internet access happens the same way of [default bridge network](../default-bridge/default_bridge.md):
  - packets go through the custom bridge (`br-xxxxx`);
  - Docker does NAT again;
  - out to your host network, then to the internet;
  - from the internet’s perspective, again, it looks like the host is making the request.

**Example:**

1. create a custom network named `my-custom-network`:

    ```commandline
    docker network create my-custom-network
    ```

2. check if the network interface is created:

    ```commandline
    ifconfig
    ```

    output:

    ```commandline
    br-344e8832134f: flags=4099<UP,BROADCAST,MULTICAST>  mtu 1500
            inet 172.18.0.1  netmask 255.255.0.0  broadcast 172.18.255.255
            ether 2e:9b:c0:ec:33:60  txqueuelen 0  (Ethernet)
            RX packets 0  bytes 0 (0.0 B)
            RX errors 0  dropped 0  overruns 0  frame 0
            TX packets 0  bytes 0 (0.0 B)
            TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
    ```
