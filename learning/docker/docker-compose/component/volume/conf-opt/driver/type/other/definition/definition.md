# Others

- in Docker Compose, volume drivers other than the default `local` driver can be used to manage persistent storage;
- these drivers allow you to store data on remote or more specialized backends rather than the host file system;
- the most common volume drivers are:
  - NFS (Network File System)
  - Azure File Storage
  - [Amazon EFS](../example/efs/efs.md)
  - Ceph
  - GlusterFS
  - Flocker
  - Portworx
