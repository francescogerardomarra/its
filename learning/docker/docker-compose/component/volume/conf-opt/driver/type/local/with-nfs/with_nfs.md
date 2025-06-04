# With NFS

- NFS (Network File System) is a distributed file system protocol that allows remote directories to be mounted and accessed over a network as if they were local file systems;
- when specifying the `local` driver with `nfs` in Docker Compose, Docker creates a volume that is managed locally on the host, but instead of using a local directory, it uses an NFS (Network File System) mount point;
- the `local` driver itself does not directly support NFS, but it can leverage NFS by specifying mount options under the `driver_opts` field in the volume configuration;
 

- Docker does not manage the NFS server or setup; it simply passes the mount options to the host’s mount command, meaning the host must have the NFS client installed and properly configured;
- the NFS volume will be mounted to a specific directory on the host, and Docker will bind that directory to the container’s volume mount point, effectively making the NFS share accessible from within the container;
- the advantage of using `local` with `nfs` is that it allows containers to access a remote file system as if it were local, enabling data sharing between containers on different hosts, provided they all mount the same NFS share.

**Example:**

```yaml
version: '3.9'

services:
  my-app:
    image: nginx:latest
    container_name: my-app
    ports:
      - "8080:80"
    volumes:
      - my-nfs-volume:/usr/share/nginx/html

volumes:
  my-nfs-volume:
    driver: local
    driver_opts:
      type: "nfs"
      o: "addr=192.168.1.100,nolock,soft,rw"
      device: ":/mnt/nfs_share"
```
