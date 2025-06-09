# Syntax

- syntax:
  ```yaml
  services:
    myservice:
      volumes:
        - myvolume:/path/in/container
        - ./host/path:/path/in/container
  ```
- supports both [named volumes](../../../../volume/mount/type/named/how-work/how_work.md) and [bind mounts](../../../../volume/mount/type/bind/how-work/how_work.md).
