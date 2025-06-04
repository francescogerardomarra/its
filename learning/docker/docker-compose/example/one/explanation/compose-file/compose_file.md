# `docker-compose.yml` File

In this chapter, we show the `docker-compose.yml` file with comments explanation:

```yaml
version: '3.8'  # Specifies the Docker Compose file format version to use

services:  # Defines the services (containers) that will be part of this application stack
  fronted:  # The frontend service definition
    build:  # Configuration for building the frontend image
      context: ./frontend  # Specifies the directory containing the Dockerfile for the frontend
    image: compose-frontend:1.0  # Names the built frontend image as "compose-frontend" with version 1.0
    ports:
      - "8090:80"  # Maps port 80 in the container to port 8090 on the host machine

  backend:  # The backend service definition
    image: compose-backend:1.0  # Uses a pre-built backend image tagged as 1.0
    secrets:  # Declares the secrets to be used by the backend
      - db_user  # Secret for database username
      - db_password  # Secret for database password
    environment:  # Sets environment variables inside the backend container
      SPRING_DATASOURCE_USERNAME_FILE: /run/secrets/db_user  # Tells Spring Boot where to find the DB username
      SPRING_DATASOURCE_PASSWORD_FILE: /run/secrets/db_password  # Tells Spring Boot where to find the DB password
    ports:
      - "8091:8080"  # Maps port 8080 in the container to port 8091 on the host
    depends_on:
      - db  # Ensures the database service starts before the backend
    deploy:  # Deployment configuration (used by Docker Swarm)
      mode: replicated  # Specifies the deployment mode as replicated
      replicas: 2  # Runs two instances of the backend service

  db:  # The database service definition
    image: postgres:15.6  # Uses the official PostgreSQL image version 15.6
    secrets:  # Declares the secrets used by the database
      - db_user  # Secret for the DB user
      - db_password  # Secret for the DB password
    environment:  # Sets environment variables for PostgreSQL
      - POSTGRES_USER_FILE=/run/secrets/db_user  # Tells PostgreSQL to read the user from the secret file
      - POSTGRES_PASSWORD_FILE=/run/secrets/db_password  # Tells PostgreSQL to read the password from the secret file
      - POSTGRES_DB=text-db  # Sets the name of the database to be created
    volumes:
      - db-data:/var/lib/postgresql/data  # Persists database data using a named volume

volumes:  # Volume definitions
  db-data:  # Declares a named volume called "db-data" for persisting database files

secrets:  # Secret definitions
  db_user:  # Declares an external secret named "db_user"
    external: true  # Indicates that this secret is managed outside of this Compose file
  db_password:  # Declares an external secret named "db_password"
    external: true  # Indicates that this secret is managed outside of this Compose file
```
