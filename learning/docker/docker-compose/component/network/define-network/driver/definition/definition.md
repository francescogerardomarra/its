# Driver Definition

- the network driver in Docker Compose specifies how the network is created and managed;
- it determines how containers connected to the network communicate with each other and with external networks;
- the driver is defined under the `networks` section in the `docker-compose.yml` file using the `driver` key;


- Docker supports several built-in network drivers, such as `bridge`, `overlay`, `host`, and `none` (they are explained within the [next](../../../../../index.md) sections);
- custom network plugins can also be used by specifying the plugin name as the driver;
- choosing the appropriate driver depends on your use case, such as single-host deployments or multi-host clusters.
