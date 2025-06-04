# Comparison with Docker Architecture

The **Docker Engine** is the core software that powers containerization, consisting of the Docker Daemon, CLI, and REST API to manage containers and images. In contrast, **Docker Architecture** describes the broader system design, including the Engine along with external components like Docker Registries and the overall interaction between clients, daemons, and resources.

**Table:**

| Aspect         | **Docker Architecture**                                                | **Docker Engine**                                |
|----------------|------------------------------------------------------------------------|--------------------------------------------------|
| **Scope**      | overall system including all components (CLI, Daemon, registry, etc.). | core software that implements containerization.  |
| **Components** | client, Daemon, objects, registry.                                     | CLI, Daemon, REST API.                           |
| **Focus**      | broader design and interaction of Docker's ecosystem.                  | specific implementation of container management. |
| **Purpose**    | illustrates how Docker's parts work together.                          | provides the tools to execute containerization.  |
