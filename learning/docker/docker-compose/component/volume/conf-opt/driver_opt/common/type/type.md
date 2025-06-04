# `type`

- the `type` option within `driver_opts` specifies the type of filesystem or mount that the volume will use;
- it is commonly used with the `local` volume driver in Docker Compose;
- the value of `type` can vary based on the operating system and mount type;


- the most common values for `type` are:
    - `none`: does not specify a particular filesystem, often used with `bind` mounts;
    - `tmpfs`: creates a temporary file system that resides in memory (RAM);
    - `nfs`: specifies a Network File System mount for shared storage;
    - `cifs`: mounts a volume using the Common Internet File System protocol (Windows shares);
    - `btrfs`: uses the Btrfs filesystem for advanced volume management and features.
- the `type` option is typically used in combination with other options such as `device` and `o` to fully define the volume configuration.

**Example:**

See an example [here](../../../driver/type/local/with-nfs/with_nfs.md).
