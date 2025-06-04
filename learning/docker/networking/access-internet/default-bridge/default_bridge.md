# Default Bridge Network

- default bridge network backed by a virtual network interface on your host machine called `docker0`;
- you run a container without specifying a network:

    ```commandline
    docker run alpine ping google.com
    ```

- Docker connects the container to the default bridge network;


- the container gets a private IP address like `172.17.0.2`.
- when the container tries to access the internet:
  - the packet goes out via the `docker0` interface;
  - Docker uses NAT (Network Address Translation) to "masquerade" the packet;
  - the packet is sent through your host’s actual network (Wi-Fi, Ethernet);
  - from the outside, it looks like it came from your host machine.

**Example:**

Check if the `docker0` network interface is present (created when Docker is installed on your machine):

```commandline
ifconfig
```

output:

```commandline
docker0: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
        inet 172.17.0.1  netmask 255.255.0.0  broadcast 172.17.255.255
        ether 42:56:19:cf:4f:b0  txqueuelen 0  (Ethernet)
        RX packets 6  bytes 168 (168.0 B)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 16  bytes 1651 (1.6 KB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
```
