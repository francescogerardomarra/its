# `myapp` Service Configuration

- you add the Traefik configuration to the myapp service so that Traefik knows how to route traffic specifically to that service, defining which paths or domains should reach it;
- without these labels, Traefik won't recognize or manage the service, meaning it won't receive any incoming HTTP requests;
- the following part must be added under the `services:` section of the [previous](../traefik/traefik.md) `docker-compose.yml` file:

```yaml
services:
  myapp:  # Define a service named 'myapp'
    image: myapp:latest  # Use the 'myapp' image (latest tag)
    deploy:
      labels:
        - "traefik.enable=true"  # Enable Traefik to manage routing for this service

        # Define router
        - "traefik.http.routers.myapp.rule=Host(`example.com`) && PathPrefix(`/api`)"  # Route requests with Host header 'example.com' and path starting with /api to the 'myapp' service
        - "traefik.http.routers.myapp.entrypoints=websecure"  # Use the 'websecure' entrypoint (typically HTTPS on port 443)
        - "traefik.http.routers.myapp.tls=true"  # Enable TLS (HTTPS) for this route
        - "traefik.http.routers.myapp.tls.certresolver=le"  # Use the Let's Encrypt certificate resolver named 'le' to obtain TLS certificates

        # Define service and target container port
        - "traefik.http.services.myapp.loadbalancer.server.port=80"  # Traefik forwards incoming traffic to port 80 inside the container
```
