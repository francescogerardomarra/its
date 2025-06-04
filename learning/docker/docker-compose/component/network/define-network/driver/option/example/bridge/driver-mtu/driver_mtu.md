# `com.docker.network.driver.mtu`

- `com.docker.network.driver.mtu` is a Docker network driver option used to set the **Maximum Transmission Unit (MTU)** size for the network interface;
- MTU defines the maximum size of a network packet that can be transmitted without fragmentation;
- the default MTU size in Docker is usually `1500`, which is common for Ethernet networks;
 

- setting the MTU to a smaller value can help avoid fragmentation when using overlay networks or VPNs, which may add overhead;
- a mismatched MTU between networks can cause packet loss, connectivity issues, or performance degradation;
- you can adjust the MTU value to match your physical network's MTU or the requirements of specific applications.

**Example:**

```yaml
version: '3.9'
services:
  web:
    image: nginx
    networks:
      - custom_bridge

networks:
  custom_bridge:
    driver: bridge
    driver_opts:
      com.docker.network.driver.mtu: "1400"
```
