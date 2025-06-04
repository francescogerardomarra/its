# Docker Volume Plugins Definition

- **provide access to external storage systems** like Portworx, GlusterFS, RexRay, Ceph, NetApp, etc.;
- **extend Docker’s native volume functionality**, allowing external volumes to be managed like local ones;
- **allow dynamic provisioning and scheduling** of volumes across Swarm nodes;


- **keep data persistent and accessible** even when containers or nodes are restarted, rescheduled, or fail;
- **enable volume-aware container placement**, ensuring services run on nodes where data is available (communicating to Swarm where the volumes are);
- **offer advanced storage features**, such as replication, encryption, snapshots, and performance tuning;


- **work by running a plugin on each host**, which interfaces with the external storage backend;
- **integrated into Docker CLI and API**, so volumes can be created and used like native Docker volumes.
