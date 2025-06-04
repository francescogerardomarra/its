# Example

- in this example, we use the same `docker-compose.yml` file of the previous [example](../../../example/compose-file/compose_file.md);
- we set 3 replicas of `app`, 2 replicas of `db` and 1 replica of `redis`;
- using Docker Swarm, the replicas will be set directly within the `docker-compose.yml` file;


- we have to do some updates to the file:
  - **remove the `container_name`**: Docker Swarm can deploy multiple replicas of the same service across multiple nodes, setting a static container name would cause conflicts;
  - **replace `restart` with `deploy` section**;
  - **manually build the image:**
    - Docker Swarm does not support building images directly from the build directive in the Compose file;
    - instead, you must build the image manually and then specify the image name in your Compose file;
    - do it in the next [run](../run/run.md) section;
    - replace `build: .` within the app service tag with `image: compose-example-image:1.0`.
- `docker-compose.yml` file:

```yaml
version: '3.8'  # Specifies the Docker Compose file format version

services:  # Defines all the services (containers) that make up the application stack

  app:  # The main application service
    image: compose-example-image:1.0 # BEFORE WAS: build: .
    # container_name: my_app # REMOVED
    ports:
      - "5000:5000"  # Maps port 5000 on the host to port 5000 in the container
    environment:
      - DATABASE_URL=mysql://user:password@db:3306/mydatabase  # Sets the database connection URL as an environment variable
    volumes:
      - ./app:/usr/src/app  # Mounts the local 'app' directory into the container for live code updates
    # restart: always # REMOVED
    deploy:  # Deployment settings for Docker Swarm
      mode: replicated  # Enables replication mode for scaling
      replicas: 3  # Sets the number of container replicas for the app service
      update_config:  # Configuration for updating the service
        parallelism: 1  # Updates one container at a time
        delay: 10s  # Waits 10 seconds between container updates to minimize disruption
      restart_policy:  # Policy for restarting containers in case of failure
        condition: on-failure  # Only restart if the container fails

  db:  # The MySQL database service
    image: mysql:latest  # Uses the latest official MySQL image from Docker Hub
    # container_name: my_db # REMOVED
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword  # Sets the MySQL root user password
      MYSQL_DATABASE: mydatabase  # Creates a database named 'mydatabase' on startup
      MYSQL_USER: user  # Defines a custom MySQL user
      MYSQL_PASSWORD: password  # Sets the password for the custom MySQL user
    ports:
      - "3306:3306"  # Maps MySQL's default port 3306 to the host machine
    volumes:
      - db_data:/var/lib/mysql  # Stores MySQL data persistently using a named volume
    # restart: always # REMOVED
    deploy:  # Deployment settings for Docker Swarm
      mode: replicated  # Enables replication mode for scaling
      replicas: 2  # Sets the number of container replicas for the db service
      update_config:  # Configuration for updating the service
        parallelism: 1  # Updates one container at a time to reduce downtime
        delay: 10s  # Waits 10 seconds between updates to ensure stability
      restart_policy:  # Policy for restarting containers in case of failure
        condition: on-failure  # Only restart if the container fails

  redis:  # The Redis caching and in-memory key-value store service
    image: redis:latest  # Uses the latest official Redis image from Docker Hub
    # container_name: my_redis # REMOVED
    ports:
      - "6379:6379"  # Maps Redis's default port 6379 to the host machine
    # restart: unless-stopped # REMOVED
    deploy:  # Deployment settings for Docker Swarm
      mode: replicated  # Enables replication mode for scaling
      replicas: 1  # Sets the number of container replicas for the redis service
      update_config:  # Configuration for updating the service
        parallelism: 1  # Updates one container at a time to maintain availability
        delay: 10s  # Waits 10 seconds between updates to reduce load
      restart_policy:  # Policy for restarting containers in case of failure
        condition: on-failure  # Only restart if the container fails

volumes:
  db_data:  # Defines a named volume for MySQL to persist database data
```
