# Shared Volumes (e.g., NFS, CIFS or Cloud Storage)

- shared volumes using a network file system;
- allow data to be shared across containers running on different swarm nodes;
- requires manual setup and correct mounting;
 

- store data on an external file server accessible over the network;
- mount the same storage location into multiple containers at the same path;
- enable stateful workloads by preserving data outside the container lifecycle;


- function independently of the container runtime and orchestrator;
- since access is shared, multiple containers can access them, possibly causing **synchronization issues**;
- parallel access is a concern that must be handled, either by the application itself or supported by the underlying storage system (like NFS with file locking);
 

- NFS provides basic file locking, but safe parallel access still depends on the application and system configuration;
- shared volumes are reliable only when used with applications that handle concurrency properly when writing data, like databases or caches;
- otherwise, use shared volumes in read-only mode.
