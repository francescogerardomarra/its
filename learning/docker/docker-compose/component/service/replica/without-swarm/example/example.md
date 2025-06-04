# Example

- in this example, we use the same `docker-compose.yml` file of the previous [example](../../../example/compose-file);
- we set 3 replicas of `app`, 2 replicas of `db` and 1 replica of `redis`;
- you need to use the **`--scale`** option in Docker Compose:

   ```commandline
   docker compose up --scale app=3 --scale db=2 --scale redis=1 -d
   ```
   
- one key limitation of Docker Compose (without Swarm) is that **each replica must have unique container names and ports**;
- to handle this, remove `container_name` from `app` and `db` (Docker will automatically assign unique names for each replica);
- also the port of the host mapping of `app` and `db` must be removed, otherwise it will try to map the same port to multiple container, which is not possible;


- in this way it will decide which port of the host use, you just specify the container port to expose:

   ```yaml
   version: '3.8'  # Specifies the Docker Compose file format version
   
   services:  # Defines all the services (containers) that make up the application stack
   
     app:  # The main application service
       build: .  # Builds the Docker image from the current directory (where Dockerfile is located)
       # container_name: my_app  # REMOVED
       ports:
         - "5000"  # Exposes the container's port 5000 (randomly mapped) BEFORE WAS: "5000:5000"
       depends_on:
         - db  # Ensures that the 'db' service starts before the 'app' service
       environment:
         - DATABASE_URL=mysql://user:password@db:3306/mydatabase  # Sets the database connection URL as an environment variable
       volumes:
         - ./app:/usr/src/app  # Mounts the local 'app' directory into the container for live code updates
       restart: always  # Ensures the container restarts automatically if it stops or crashes
   
     db:  # The MySQL database service
       image: mysql:latest  # Uses the latest official MySQL image from Docker Hub
       # container_name: my_db  # REMOVED
       environment:
         MYSQL_ROOT_PASSWORD: rootpassword  # Sets the MySQL root user password
         MYSQL_DATABASE: mydatabase  # Creates a database named 'mydatabase' on startup
         MYSQL_USER: user  # Defines a custom MySQL user
         MYSQL_PASSWORD: password  # Sets the password for the custom MySQL user
       ports:
         - "3306"  # Exposes the container's MySQL port (randomly mapped) BEFORE WAS: "3306:3306"
       volumes:
         - db_data:/var/lib/mysql  # Stores MySQL data persistently using a named volume
       restart: always  # Ensures the container restarts automatically if it stops or crashes
   
     redis:  # The Redis caching and in-memory key-value store service
       image: redis:latest  # Uses the latest official Redis image from Docker Hub
       ports:
         - "6379:6379"  # Maps Redis's default port 6379 to the host machine
       restart: unless-stopped  # Restarts the container unless manually stopped
   
   volumes:
     db_data:  # Defines a named volume for MySQL to persist database data
   ```
