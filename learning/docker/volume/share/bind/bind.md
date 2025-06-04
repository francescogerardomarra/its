# Bind Mounts

- you can share a local folder between multiple containers:

    ```commandline
    docker run --name container-1 -v /home/user/data:/app/data my-image-1
    docker run --name container-2 -v /home/user/data:/tmp/my-folder my-image-2
    ```

- **all concurrency problems can happen when multiple containers share the same bind mount and access the same files without proper coordination**;
- Docker doesn't manage them automatically;


- race condition can happen:
  1. `container-1` opens `file A`;
  2. `container-2` opens `file A`;
  3. `container-1` writes `Hello dog` and save;
  4. `container-2` writes `Hello cat` and save;
  5. file will contain only `Hello cat`, where maybe the purpuse was to have both strings saved within the file.

- in this scenarios, you need to implement mechanisms at application level to prevent these issues (e.g. using queue).
