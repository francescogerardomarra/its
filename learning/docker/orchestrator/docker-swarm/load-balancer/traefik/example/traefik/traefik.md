# Traefik Configuration

- see in the [previous](../definition/definition.md) section the explanation of the following Traefik configuration;
- see the configuration you need to add to the service so that Traefik can route traffic to it [here](../myapp/myapp.md).

**`docker-compose.yml` file:**

```yaml
services:
  traefik:  # Define a service named 'traefik'
    image: traefik:v2.10  # Use the Traefik version 2.10 image from Docker Hub
    command:  # Override default Traefik command with custom flags
      - --providers.docker  # Enable Docker provider so Traefik can read Docker metadata
      - --providers.docker.swarmMode=true  # Enable Swarm mode support for Docker provider
      - --entrypoints.web.address=:80  # Define an entrypoint 'web' listening on port 80 (HTTP)
      - --entrypoints.websecure.address=:443  # Define an entrypoint 'websecure' listening on port 443 (HTTPS)
      - --certificatesresolvers.le.acme.httpchallenge=true  # Enable ACME HTTP challenge for Let's Encrypt
      - --certificatesresolvers.le.acme.httpchallenge.entrypoint=web  # Use the 'web' entrypoint for the HTTP challenge
      - --certificatesresolvers.le.acme.email=you@example.com  # Email address used for Let's Encrypt registration and recovery
      - --certificatesresolvers.le.acme.storage=/letsencrypt/acme.json  # File to store ACME certificates and account info
    ports:
      - 80:80  # Map host port 80 to container port 80 (HTTP)
      - 443:443  # Map host port 443 to container port 443 (HTTPS)
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock:ro  # Mount Docker socket in read-only mode for service discovery
      - traefik_certificates:/letsencrypt  # Use named volume to store Let's Encrypt data
    deploy:
      mode: global  # Deploy one instance of Traefik on every Swarm manager node
      placement:
        constraints:
          - node.role == manager  # Only run Traefik on Swarm manager nodes
    networks:
      - traefik  # Connect the service to the external 'traefik' network

volumes:
  traefik_certificates:  # Define a named volume to persist Let's Encrypt certificate data

networks:
  traefik:
    external: true  # Use an external Docker network named 'traefik' (must be created beforehand)
```
