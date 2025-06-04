# Size

- the `size` option in Docker Compose volumes is used to specify the maximum size of the volume when using the `local` volume driver;
- the size option needs to be included as part of the o (options) parameter;
- without specifying the size option, the volume size will be unlimited by default;
 
 
- it is supported only on Linux and works with volume drivers that support size quotas, such as `local` with `xfs` or `btrfs` file systems;
- size quotas like `xfs` or `btrfs` are filesystem features that allow you to set limits on disk usage for directories or volumes, helping to control storage consumption;
- the size value should be specified as a string representing the size limit, for example, `100m`, `2g`, or `1t`;
 

- it is essential to have a file system that supports quota management, otherwise, the size limit will not be enforced;
- specifying the `size` option helps to control disk space usage by limiting how much data can be stored within a given volume.

**Example:**

See an example [here](../example/tmpfs/tmpfs.md).
