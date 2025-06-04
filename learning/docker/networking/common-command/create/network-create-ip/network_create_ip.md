# `docker network create` with Specific IP Range

- this command is the same of previous [docker network create](../network-create/network_create.md), but it adds custom flags to define a specific IP range and gateway within a user-defined subnet:

    ```commandline
    docker network create \
      --driver bridge \
      --subnet 192.168.1.0/24 \
      --gateway 192.168.1.1 \
      <network-name>
    ```

- `--subnet 192.168.1.0/24`:
  - this sets the subnet range for the network;
  - the IP range goes from `192.168.1.1` to `192.168.1.254`;
  - `/24` means the subnet mask is `255.255.255.0`, allowing 254 usable IP addresses.
- `--gateway 192.168.1.1`:
  - this sets the gateway IP address for the network, i.e., the IP of the virtual router Docker uses to manage traffic into and out of the network.