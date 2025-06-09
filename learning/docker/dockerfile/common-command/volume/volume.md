# `VOLUME`

- **purpose:**
  - defines a **persistent storage location** inside the container, preventing data loss when the container stops;
  - allows data to be **shared between containers** and between the container and the host;
  - automatically creates a **managed anonymous volume** if no external volume is specified during `docker run`;
  - it means if no host path is specified, the volume will be mapped within the `/var/lib/docker/volumes` in the host machine.

- **syntax:**

    ```dockerfile
    VOLUME <mountpoint>
    VOLUME ["<mountpoint1>", "<mountpoint2>"]
    ```

- **example:**
  - **create a volume for persistent storage:**
  
    ```dockerfile
    VOLUME /data
    ```
  - **define multiple volume mount points:**
  
    ```dockerfile
    VOLUME ["/data", "/logs"]
    ```
  - **run a container and specify a named volume instead of an anonymous one:**
  
    ```sh
    docker run -v mydata:/data my-container
    ```

- **specified volume VS anonymous volume:**
  - **specified volume (`-v /host/path:/container/path`)**: 
    - directly links a directory from the **host machine** to a directory inside the container; 
    - allowing full control over where data is stored and enabling easy access and modification from the host.
  - **anonymous volume (`VOLUME /container/path` and `docker run` without `-v`)**: 
    - Docker automatically creates a **managed volume** in its storage on the host (`/var/lib/docker/volumes`);
    - ensuring **persistent data** but without direct control over its host location.  
