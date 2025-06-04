# `device`

- the `device` option within `driver_opts` specifies the path to the device or directory on the host machine that will be mounted as a volume;
- it is commonly used when configuring `bind` mounts or mounting network filesystems (like `nfs` or `cifs`);
- the value of `device` should be an absolute path to the desired mount location or device;


- when used with a `bind` mount, it points to a specific directory on the host (e.g., `/data`);
- when used with a network filesystem (like `nfs`), it points to the remote server and export path (e.g., `192.168.1.100:/exported/path`);
- when used with storage drivers like `btrfs`, it points to a block device (e.g., `/dev/sdb1`);


- the `device` option is usually combined with the `type` option to specify the type of filesystem or mount;
- if the `device` path does not exist on the host, Docker will not create it automatically and will throw an error during container startup.

**Example:**

- see also another example [here](../../../driver/type/local/with-nfs/with_nfs.md);
- see what `o` option is [here](../o/definition/definition).

```yaml
version: '3.9'

services:
  web:
    image: nginx:latest
    container_name: my_nginx
    ports:
      - "8080:80"
    volumes:
      - my_bind_volume:/usr/share/nginx/html

volumes:
  my_bind_volume:
    driver: local
    driver_opts:
      type: none
      device: /path/to/host/directory
      o: bind
```
