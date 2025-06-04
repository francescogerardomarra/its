# Differences from Shared Volumes

- **[shared volumes](../../shared/shared.md) rely on manual host setup** (e.g., mounting NFS or CIFS paths on each node), while **volume plugins integrate with Docker and automate provisioning**;
- shared volumes are typically just **bind mounts on a shared network file system**, offering no Swarm awareness or dynamic behavior;
- volume plugins **track volume state, support Swarm services, and offer scheduling intelligence**, which shared volumes lack;
 

- with plugins, **volumes follow services during failover or scaling**, but with shared volumes, you must manually ensure all nodes have access;
- shared volumes may face **performance or concurrency issues**, especially with write-heavy applications, while volume plugins often provide **optimized, distributed, and concurrent-safe storage**;
- plugins offer **high availability and self-healing capabilities**, whereas shared volumes depend on the reliability of a single NFS/CIFS server.
