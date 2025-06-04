# `tmpfs`

**`docker-compose.yml` file:**

```yaml
version: '3.9'
services:
  app:
    image: nginx
    volumes:
      - mytmpfs:/tmpfs

volumes:
  mytmpfs:
    driver: local
    driver_opts:
      type: tmpfs
      device: tmpfs
      o: size=64m,uid=1000,gid=1000
```

**Above `docker-compose.yml` explanation:**

- a `tmpfs` is a temporary filesystem that resides entirely in memory (RAM), providing fast, ephemeral storage that is cleared when the system or container stops;
- the `type: tmpfs` tells Docker to use an in-memory temporary filesystem;
- the `device: tmpfs` is more of a Linux kernel requirement than a Docker-specific one;


- it essentially instructs Docker to use the `tmpfs` device, which is a special device type representing in-memory storage;
- the `o` option is used to specify mount options as a comma-separated list;
- `size=64m` sets the maximum size of the temporary filesystem to 64 MB;
 

- `uid=1000` and `gid=1000` set the ownership of the mounted volume to user ID 1000 and group ID 1000;
- this configuration is useful for creating fast, in-memory storage that is automatically cleared when the container stops.
