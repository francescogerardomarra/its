# Custom Definition

- a custom internal volume driver is a program you write (or configure) that integrates with Docker to manage storage using your own infrastructure, instead of relying on Docker’s built-in local storage or external plugins;
- it implements Docker’s volume plugin API, and Docker communicates with it over a Unix socket or TCP port;
- for example, when you run `docker volume rm my_custom_volume`:
  - Docker sends a `POST /VolumeDriver.Remove` request to the **custom volume driver** you configured (via the plugin socket);
  - it expects the plugin to handle **unmounting and cleanup logic** (e.g., deleting remote data, releasing resources);
  - if your plugin does **not implement** that API endpoint or returns an error, Docker will fail with an error like:
  
    ```
    Error response from daemon: remove my_custom_volume: volume driver "yourdriver" failed to remove volume: not implemented
    ```
