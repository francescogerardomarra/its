# nfs

- a volume with `driver: nfs` in Docker Compose specifies that the volume itself is managed directly by an NFS volume plugin, rather than by Docker's built-in local volume driver;
- the `nfs` driver requires a third-party volume plugin that explicitly supports NFS (e.g., `netshare/nfs` or other NFS volume plugins);
- it allows for more advanced NFS features and configuration options directly managed by the plugin rather than Docker itself;


- in contrast, a volume with [driver: local](../local/with-nfs/with_nfs.md) and `type: nfs` uses Docker's native local volume driver to create an NFS mount point;
- the `local` driver simply mounts the NFS share as if it were a local filesystem, using `driver_opts` to specify the NFS configuration;
- the main difference is that `driver: nfs` uses an external NFS volume plugin, while `driver: local` of type `nfs` uses Docker's built-in local driver to manage the NFS mount;


- the `nfs` driver can provide better integration with NFS-specific features through the plugin, while the `local` driver relies on basic NFS client support on the host system.

**Example:**

```yaml
volumes:
    myvolume:
      driver: nfs
      driver_opts:
        share: "192.168.1.100:/path/to/share"
        version: "4"
```
