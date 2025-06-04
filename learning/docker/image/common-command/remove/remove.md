# Remove

- **remove a specific image**:

    ```commandline
    docker rmi <repository>:<tag>
    ```

- **remove [dangling images](../../tag-and-meta/dangling/intro/intro.md)**:

    ```commandline
    docker image prune
    ```

- **remove any image not currently used by a running or stopped container, regardless of whether it has a tag**:

    ```commandline
    docker image prune -a
    ```

- **remove [dangling images](../../tag-and-meta/dangling/intro/intro.md) older than 24 hours**:

    ```commandline
    docker image prune --filter "until=24h"
    ```

- **like the `docker image prune -a`, but removes images older than 24 hours**:

  ```commandline
  docker image prune -a --filter "until=24h"
  ```

- **remove only unused resources and dangling images, including**:
  - stopped containers: containers that are not running;
  - [dangling images](../../tag-and-meta/dangling/intro/intro.md): images without a tag and not referenced by any container;
  - unused networks: networks that are not connected to any container;
  - build cache: intermediate build layers no longer needed;
  - does not remove tagged images, even if they are unused.

    ```commandline
    docker system prune
    ```

- **remove unused data, including images, networks, and build cache**:
  - performs a more aggressive cleanup than `docker system prune`;
  - it removes:
    - everything that `docker system prune` does;
    - all unused images, this includes tagged images that are not associated with any running or stopped containers.

    ```commandline
    docker system prune --all
    ```
