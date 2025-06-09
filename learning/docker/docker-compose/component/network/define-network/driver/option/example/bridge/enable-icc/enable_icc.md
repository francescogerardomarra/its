# `com.docker.network.bridge.enable_icc`

- `com.docker.network.bridge.enable_icc` is a Docker network driver option that controls whether containers on the same bridge network can communicate with each other;
- ICC stands for **Inter-Container Communication**, and enabling it allows containers connected to the same bridge network to directly exchange data;
- the option is typically set to `true` (the default value), which means containers can freely communicate within the network;


- when set to `false`, communication between containers on the same network is restricted, effectively isolating them from each other;
- this setting is useful when you want to enhance security by preventing containers from talking to each other without explicit configuration;
- it disables inter-container communication on a bridge network but still allows external connectivity if configured;


- while [driver: none](../../../../type/none/none.md) sets full isolation without any network access.

**Example:**

```yaml
version: '3.9'
services:
  app1:
    image: nginx
    networks:
      - isolated_bridge

  app2:
    image: httpd
    networks:
      - isolated_bridge

networks:
  isolated_bridge:
    driver: bridge
    driver_opts:
      com.docker.network.bridge.enable_icc: "false"
```
