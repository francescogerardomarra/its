# Multiple App With Containers

1. **setup**:
   - single VM;
   - the VM has a container runtime (e.g., Docker) installed;
   - applications (`App A`, `App B`, `App C`) are containerized with their dependencies.

2. **characteristics**:
   - **resource allocation**:
     - each container packages the application and its dependencies, reducing conflicts;
     - containers share the host OS kernel but can be allocated specific CPU/memory limits using container tools.
   - **resource isolation**:
     - containers provide better isolation;
     - if `App A` consumes too much CPU, it doesn't directly impact `App B` or `App C`.
   - **security**:
     - containers are isolated from each other, reducing the risk of one application affecting others.
   - **portability**:
     - containers can be easily moved between environments (e.g., from a developer's laptop to a production server).

3. **benefits**:
   - applications are isolated, so a crash or high-resource usage in `App A` doesn’t affect `App B` or `App C`;
   - easier to manage dependencies (e.g., `App A` can use Python 3.9, `App B` can use Python 3.7);
   - scaling a specific application (e.g., running two instances of `App C`) is straightforward.
