# External

- docker compose allows you to connect containers to external networks, enabling communication between containers and other services outside the compose application;
- to use an external network, you must define the network in your `docker-compose.yml` file under the `networks` section;
- set the `external` property of the network to `true`, which tells Docker to use an existing network rather than creating a new one;


- the network name specified in the compose file must match the name of the existing Docker network;
- containers launched by the compose file will be automatically connected to the external network upon creation;
- you can specify the network in the `services` section of the compose file to assign services to that network;


- if the external network does not exist when you run `docker compose up`, Docker will throw an error stating that the specified network cannot be found, and the compose process will be aborted;
- see the example within the [overlay](../define-network/driver/type/overlay/example/example.md) section.

**External:**

```yaml
version: '3.9'

services:
  webapp:
    image: nginx:latest
    container_name: my-webapp
    networks:
      - my-external-network

networks:
  my-external-network:
    external: true
```
