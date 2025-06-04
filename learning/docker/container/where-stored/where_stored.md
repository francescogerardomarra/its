# Where Are Containers Stored?

- Docker containers are stored on the host machine, specifically within the Docker installation's default storage directory or a user-defined directory;
- on most systems, Docker uses its default storage location to keep containers, images, volumes, and other data.
- containers and related files are stored in:

    ```java
    /var/lib/docker/
    ```

- if you want to use a different storage path, you can configure it in Docker's daemon settings:
  - modify the data-root field in the Docker daemon configuration file (usually located at `/etc/docker/daemon.json`):
  
     ```java
     {
        "data-root": "/custom/path/to/docker"
     }
     ```
  
  - restart the Docker service for changes to take effect:

      ```java
      sudo systemctl restart docker
      ```
