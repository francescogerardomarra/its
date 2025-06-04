# Example

- in this chapter, we show a complete `docker-compose.yml` file that includes most of the more important configurations;
- **if you run this file it will not work since it requires some external manual configurations (like creating a web image `Dockerfile`)**;
- we created this example just to show how to a `docker-compose.yml` file in a real world production environment might appear.

**`docker-compose.yml` file:**

```yaml
version: '3.8'  # Docker Compose file format version

services:  # Define all the application services here
  web:  # Web frontend service
    build:  # Build the image from a Dockerfile
      context: ./web  # Directory containing Dockerfile
    image: myapp/web:latest  # Tag the built image
    ports:
      - "80:80"  # Map host port 80 to container port 80
    environment:
      - NODE_ENV=production  # Set environment variable
      - API_URL=http://api:5000  # URL for backend API
    volumes:
      - web-data:/usr/src/app/data  # Mount named volume into container
    depends_on:
      api:
        condition: service_healthy  # Wait for API to be healthy before starting
    restart: always  # Always restart the container on failure or reboot
    networks:
      - frontend  # Connect to frontend network
      - backend  # Connect to backend network
    healthcheck:  # Define a health check to determine if service is healthy
      test: ["CMD", "curl", "-f", "http://localhost"]  # Command to check health
      interval: 30s  # Wait time between checks
      timeout: 10s  # Time to wait for a response
      retries: 3  # Retry count before marking as unhealthy
    logging:
      driver: "json-file"  # Logging driver to use
      options:
        max-size: "10m"  # Maximum size per log file
        max-file: "3"  # Max number of log files to keep

  api:  # Backend API service
    image: myapp/api:latest  # Use a prebuilt image
    ports:
      - "5000:5000"  # Expose port 5000
    environment:
      API_KEY: ${API_KEY}  # Use environment variable from .env or shell
    secrets:
      - api_secret  # Mount a secret into the container
    configs:
      - source: app_config  # Mount a config file into the container
        target: /etc/app_config.yaml  # Location in the container
    volumes:
      - ./api/logs:/var/log/api  # Bind-mount logs directory
    restart: on-failure  # Restart only if the container exits with an error
    networks:
      - backend  # Connect to backend network
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:5000/health"]  # Health check for API
      interval: 10s
      retries: 5
    deploy:  # Optional deployment configuration (mainly for Swarm)
      resources:
        limits:
          cpus: '0.50'  # Limit container to 0.5 CPUs
          memory: 512M  # Limit memory to 512MB

  db:  # PostgreSQL database service
    image: postgres:13  # Use official PostgreSQL image
    environment:
      POSTGRES_USER: myuser  # DB username
      POSTGRES_PASSWORD: mypass  # DB password
      POSTGRES_DB: mydb  # DB name
    volumes:
      - db-data:/var/lib/postgresql/data  # Mount named volume for persistent storage
    restart: unless-stopped  # Restart unless the container is manually stopped
    networks:
      - backend  # Connect to backend network

volumes:  # Define named volumes for persistent data
  db-data:  # Volume for the PostgreSQL database
  web-data:  # Volume for the web service's data

networks:  # Define custom networks
  frontend:  # Frontend network for public services
  backend:  # Backend network for internal communication

secrets:  # Define secrets (secure files)
  api_secret:
    file: ./secrets/api_secret.txt  # Path to secret file

configs:  # Define configs (non-secret configuration files)
  app_config:
    file: ./configs/app_config.yaml  # Path to config file
```
