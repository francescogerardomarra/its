# Best Practices

- use **named volumes**, not bind mounts, for better portability and management;
- for critical data across nodes, use **volume plugins** or **NFS/CIFS**;
- avoid relying on local volumes for stateful services unless they are **pinned to specific nodes** or **backed by external storage**.
