# Cloud or Third Part

- a cloud or third-party driver is a plugin that Docker uses to interface with external storage systems;
- instead of storing volume data on the host's filesystem, Docker uses the driver to interact with another storage backend;
- this could be:
  - AWS EBS (Elastic Block Store);
  - Google Cloud Persistent Disks
  - Azure Disks
  - [NFS (Network File System)](../nfs/definition/definition.md)
  - GlusterFS
  - Portworx
  - Ceph
  - and many more...


- these drivers are typically used in production environments where:
  - storage needs to be shared across multiple Docker hosts (e.g., in a Swarm or Kubernetes cluster);
  - you want high availability, backup, or performance features provided by the external storage system.
