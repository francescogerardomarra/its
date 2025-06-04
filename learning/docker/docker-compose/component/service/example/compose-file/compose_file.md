# `docker-compose.yml` File

- in this example, we have three services (`app`, `db` and `redis`) that compose the stack;
- `docker-compose.yml` file:

    ```yaml
    version: '3.8'  # Specifies the Docker Compose file format version
    
    services:  # Defines all the services (containers) that make up the application stack
    
      app:  # The main application service
        build: .  # Builds the Docker image from the current directory (where Dockerfile is located)
        container_name: my_app  # Assigns a custom name to the container
        ports:
          - "5000:5000"  # Maps port 5000 on the host to port 5000 in the container
        depends_on:
          - db  # Ensures that the 'db' service starts before the 'app' service
        environment:
          - DATABASE_URL=mysql://user:password@db:3306/mydatabase  # Sets the database connection URL as an environment variable
        volumes:
          - ./app:/usr/src/app  # Mounts the local 'app' directory into the container for live code updates
        restart: always  # Ensures the container restarts automatically if it stops or crashes
    
      db:  # The MySQL database service
        image: mysql:latest  # Uses the latest official MySQL image from Docker Hub
        container_name: my_db  # Assigns a custom name to the container
        environment:
          MYSQL_ROOT_PASSWORD: rootpassword  # Sets the MySQL root user password
          MYSQL_DATABASE: mydatabase  # Creates a database named 'mydatabase' on startup
          MYSQL_USER: user  # Defines a custom MySQL user
          MYSQL_PASSWORD: password  # Sets the password for the custom MySQL user
        ports:
          - "3306:3306"  # Maps MySQL's default port 3306 to the host machine
        volumes:
          - db_data:/var/lib/mysql  # Stores MySQL data persistently using a named volume
        restart: always  # Ensures the container restarts automatically if it stops or crashes
    
      redis:  # The Redis caching and in-memory key-value store service
        image: redis:latest  # Uses the latest official Redis image from Docker Hub
        container_name: my_redis  # Assigns a custom name to the container
        ports:
          - "6379:6379"  # Maps Redis's default port 6379 to the host machine
        restart: unless-stopped  # Restarts the container unless manually stopped
    
    volumes:
      db_data:  # Defines a named volume for MySQL to persist database data
    ```
