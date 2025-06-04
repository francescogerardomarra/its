# Aliases

- network aliases in Docker Compose are used to assign additional hostnames to a service within a Docker network;
- they allow services to be accessed using more than one hostname, improving flexibility in inter-service communication;
- you can define network aliases under the "networks" section of a service in your `docker-compose.yml` file;


- once the aliases are defined, the service can be accessed using any of the specified aliases from other containers within the same network;
- using aliases is especially useful when you want to refer to the same service using different names, for instance, in a multi-environment setup or when maintaining backward compatibility;
- if multiple services use the same alias, Docker will round-robin traffic among them, creating simple load balancing.

**Example:**

- within the following example, another service connected to `my_custom_network` can access `app` service using the following names: 
  - `app`;
  - `myapp.local`;
  - `myapp.internal`.
- `docker-compose.yml` file:

    ```yaml
    version: '3.9'
    
    services:
      app:
        image: nginx:alpine
        container_name: my_app_container
        ports:
          - "8080:80"
        networks:
          my_custom_network:
            aliases:
              - myapp.local
              - myapp.internal
    
    networks:
      my_custom_network:
        driver: bridge
    ```
