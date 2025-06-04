# `nfs` Definition

- the nfs driver in Docker volumes allows containers to use remote storage via the NFS (Network File System) protocol;
- it’s commonly used to share persistent data across multiple Docker hosts or containers;
- Docker by default uses the [local](../../local/local.md) volume driver, but you can mount volumes from NFS shares using the `nfs` driver (technically it's still a `local` driver with options for NFS);


- there is no separate `nfs` driver built into Docker;
- instead, you use the local driver and specify `type=nfs` or mount NFS manually using `--mount`.
