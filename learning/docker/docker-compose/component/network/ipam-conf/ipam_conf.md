# IPAM Configuration

- ipam (IP Address Management) configuration in Docker Compose allows you to define custom network settings for your containers;
- you configure ipam under the `networks` section of the `docker-compose.yml` file;
- ipam configuration requires the `driver` option, typically set to `bridge`, and an `ipam` block containing a list of configurations;
 

- within the ipam block, the `config` subsection holds details about subnet, gateway, and IP range;
- the `subnet` specifies the IP range available for containers within the network;
- the `gateway` sets the IP address of the network’s gateway (usually the first IP in the subnet);
 

- you can also define `ip_range` to limit the range of IPs Docker can assign from the subnet;
- to set a static IP for a service, use the `networks` section under the service definition and specify the [ipv4_address](../static-ip/static_ip.md) within the service configuration;
- multiple networks can be defined within the same Compose file, each with separate ipam configurations.

**Example:**

```yaml
version: '3.9'

services:
  web:
    image: nginx:latest
    container_name: web_server
    ports:
      - "8080:80"
    networks:
      my_network:

networks:
  my_network:
    driver: bridge
    ipam:
      config:
        - subnet: 192.168.50.0/24
          gateway: 192.168.50.1
```
