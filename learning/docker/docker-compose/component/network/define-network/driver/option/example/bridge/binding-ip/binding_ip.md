# `com.docker.network.bridge.host_binding_ipv4`

- `com.docker.network.bridge.host_binding_ipv4` is a Docker network driver option used to specify the default IP address for port bindings on the host system;
- it defines the IP address that containers will bind to when exposing ports to the host, instead of using `0.0.0.0` (which means all interfaces);
- typically set to a specific IP address (e.g., `192.168.1.100`) to control which network interface the container ports are exposed on;
 

- it is useful when the host has multiple network interfaces, and you want to restrict container access to a particular interface or IP;
- setting this option enhances security and network control by limiting exposure to specific interfaces or IP addresses.

**Example:**

```yaml
version: '3.9'
services:
  web:
    image: nginx
    ports:
      - "8080:80"
    networks:
      - custom_bridge

networks:
  custom_bridge:
    driver: bridge
    driver_opts:
      com.docker.network.bridge.host_binding_ipv4: "192.168.1.100"
```
