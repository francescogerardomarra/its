# `driver_opts` Definition

- `driver_opts` is a key under the `volumes` section in a Docker Compose file;
- it specifies options to configure the volume driver;
- each option is defined as a key-value pair, where the key represents the specific configuration parameter and the value is the configuration value;


- it is primarily used when the volume driver requires specific settings, such as storage location or size limits;
- the actual supported options depend on the chosen volume driver;
- for example, if using the `local` driver, common options include `type`, `device`, and `o`, which specify mount type, device path, and mount options respectively;


- for other drivers, like `nfs` or cloud-based volume plugins, the options differ and should be checked in the respective driver's documentation.
