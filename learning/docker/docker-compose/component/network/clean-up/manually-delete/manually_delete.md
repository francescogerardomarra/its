# Manually Delete a Used Network

- in this chapter, we try to manually delete a currently used network;
- the network is used by a service of the Docker Compose stack set in the [previous](../../check/example/check/check.md) example.

**Steps:**

1. see the active networks, open a terminal and run:

   ```commandline
   docker network ls
   ```
   
   output:
   
   ```commandline
   NETWORK ID     NAME                            DRIVER    SCOPE
   2e3e3873877d   desktop_custom_network_1        bridge    local
   12bccd11e524   desktop_custom_network_2        bridge    local
   99531153fe6f   desktop_custom_network_3        bridge    local
   ef0f841c035a   desktop_default                 bridge    local
   ```

2. try to remove `desktop_custom_network_2`:

   ```commandline
   docker network rm desktop_custom_network_2
   ```
   
   output:
   
   ```commandline
   Error response from daemon: error while removing network: network desktop_custom_network_2 id 12bccd11e52426cb60b73f5cb102c34e0c6b52c22b53a2bd085e30370568fd52 has active endpoints
   exit status 1
   ```

The `desktop_custom_network_2` network is currently used by `service2`, so it's **not** possible to remove it.
