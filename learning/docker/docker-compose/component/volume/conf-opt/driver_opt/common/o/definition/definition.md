# `o` Definition

- `driver_opts` is used in Docker Compose to specify options for volume drivers;
- it is used as a dictionary where each key-value pair corresponds to a specific driver option;
- the `o` option is commonly used with the `local` volume driver to set mount options, such as specifying the file system type or mounting flags;


- you can specify multiple mount options using a comma-separated list, like `o: bind,rw` for read-write access;
- typical values for `o` include `bind`, `ro` (read-only), `rw` (read-write), `nosuid`, `nodev`, and others depending on the underlying file system;
- `device` specifies the host path that will be mounted into the container;


- using the `o` option allows you to fine-tune how volumes are mounted and how they behave.

**Example:**

See an example [here](../../type/type.md).
