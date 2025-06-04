# Prune

- this command is used to remove all **unused** Docker volumes from your system:

    ```commandline
    docker volume prune
    ```


- **it deletes all volumes that are not currently being used by any containers**;
- this helps free up disk space by getting rid of leftover volumes from containers that have been deleted;


- **even if the container is stopped, Docker keeps track that this container is still "using" the volume (because you could start it again)**;
- only when you delete the container (e.g., with `docker rm`) does Docker consider the volume unused (assuming no other container uses it);
- see more details in [volumes lifetime chapter](../../mount/lifetime/lifetime.md);


- it will prompt you to confirm, to skip the confirmation prompt, add the `-f` (force) flag:

  ```commandline
  docker volume prune -f
  ```
