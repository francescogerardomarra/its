# Options Definition

- `driver_opts` is an optional configuration in Docker Compose used to specify additional options for a network driver;
- it is defined under the `networks` section of the `docker-compose.yml` file;
- the syntax follows the pattern:

  ```yaml
  networks:
    my_network:
      driver: bridge
      driver_opts:
        com.docker.network.bridge.name: custom_bridge_name
  ```  
- different network drivers (like `bridge` or `overlay`) support different options;
- the host network driver does not use driver_opts, as it directly shares the host's network stack and does not support additional options;
- the `driver_opts` allow customization of network parameters, such as bridge name, gateway, and subnet;


- typical use cases include setting custom MTU sizes or specifying bridge names for Linux containers (MTU (Maximum Transmission Unit) size is the largest packet size that can be transmitted over a network without fragmentation);
- if you omit `driver_opts`, Docker will use the default network settings.
