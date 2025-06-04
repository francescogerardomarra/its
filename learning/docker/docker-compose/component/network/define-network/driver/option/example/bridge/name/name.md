# `com.docker.network.bridge.name`

- sets the name of the bridge interface on the host, e.g., `custom_bridge_name`;
- inside Docker (Docker Compose and CLI): uses custom_bridge as the logical network name;
- on the Host System (Linux): uses my_custom_bridge as the actual bridge interface:
  
```yaml
services:
  web:
    image: nginx
    networks:
      - custom_bridge
  
networks:
  custom_bridge:
    driver: bridge
    driver_opts:
      com.docker.network.bridge.name: my_custom_bridge
```



- `com.docker.network.bridge.enable_ip_masquerade`: enables IP masquerading (NAT) for outbound traffic, usually set to `true`;
- `com.docker.network.bridge.enable_icc`: enables inter-container communication on the same network, typically `true`;
 

- `com.docker.network.bridge.host_binding_ipv4`: specifies the IP address for port binding, e.g., `0.0.0.0`;
- `com.docker.network.driver.mtu`: sets the MTU (Maximum Transmission Unit) size, e.g., `1500`.
