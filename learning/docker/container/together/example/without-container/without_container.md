# Multiple App Without Containers

1. **setup**:
   - single VM;
   - the VM has an operating system (e.g., Ubuntu);
   - applications (`App A`, `App B`, `App C`) are installed and run directly on the VM.

2. **characteristics**:
   - **resource allocation**:
     - each application uses system resources (CPU, memory, storage) but shares the same OS kernel and libraries;
     - if `App A` needs Python 3.9 and `App B` needs Python 3.7, conflicts may arise unless virtual environments or manual configurations are used.
   - **resource isolation**:
     - no strict isolation between applications;
     - if `App A` consumes too much CPU or memory, it can affect `App B` and `App C`.
   - **security**:
     - all applications run as processes under the same OS, which means vulnerabilities in one application can potentially impact others.

3. **issues**:
   - resource contention if apps scale;
   - managing dependencies and isolation is complex;
   - a crash in `App A` may affect the other apps.
