# Remove By Container Id
1. to identify the container you want to remove, list all containers and their IDs:

    ```bash
    docker ps -a
    ```

2. to remove a single container (replace `<container_id>` with the ID or name of the container you want to remove):

   ```bash
   docker rm <container_id>
   ```

3. to remove multiple containers:

   ```bash
   docker rm <container_id1> <container_id2> <container_id3>
   ```

4. to remove all stopped containers:

   ```bash
   docker container prune
   ```

**Notes**
- **running containers cannot be removed**: 
  - you need to stop a container before you can remove it;
  - use `docker stop <container_id>` to stop a running container first.
- **force removal of a running container**: if you need to force remove a running container, you can use the `-f` (force) flag:

  ```bash
  docker rm -f <container_id>
  ```

- the above command is helpful if the container is stuck or unresponsive and needs to be removed immediately.

