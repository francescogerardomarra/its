# Defined Networks

When you explicitly define networks in your `docker-compose.yml`, you're creating custom named networks; you can add specific configurations if you want.

**Example:**

- `docker-compose.yml` file:

    ```yaml
    version: "3.8"
    
    services:
      app:
        image: my-app
        networks:
          - frontend
    
      api-service:
        image: my-api-service:1.0
    
      db:
        image: postgres
        networks:
          - backend
    
    networks:
      frontend:
      backend:
    ```

- within the above `docker-compose.yml` file you're telling Docker to create two named networks: `frontend` and `backend`;
- since `api-service` doesn't specify any networks it connects to the [default network](../default/default.md);
 

- each service connects to the network(s) you assign, otherwise to the default network;
- this gives you control over communication, isolation, and network config (e.g., setting driver, subnets, etc.).
