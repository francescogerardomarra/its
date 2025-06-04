# `com.docker.network.bridge.enable_ip_masquerade`

- `com.docker.network.bridge.enable_ip_masquerade` is a Docker network driver option that controls whether IP masquerading (NAT) is enabled on the bridge network;
- IP masquerading allows containers to communicate with external networks (like the internet) by using the host’s IP address as the source address;
- it is typically set to `true` (the default value), which means outgoing container traffic will be masqueraded as the host’s IP address;
 

- when set to `false`, containers will directly use their own IP addresses for outbound traffic, which may not be routable on external networks;
- enabling masquerading is useful when you want to provide internet access to containers while keeping their internal IPs hidden.

**Example:**

```yaml
version: '3.9'
services:
  web:
    image: nginx
    networks:
      - my_bridge

networks:
  my_bridge:
    driver: bridge
    driver_opts:
      com.docker.network.bridge.enable_ip_masquerade: "true"
```
