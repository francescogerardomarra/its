# `containerd` VS `runc`

- `containerd`:
  - handles the overall management of container lifecycles:
    - pulls images from registries;
    - manages container storage;
    - coordinates networking.
  - delegates the low-level task of actually creating and running the container to **runc**.

- `runc`:
  - receives instructions from **containerd** to create and start the container;
  - uses Linux kernel features (e.g., namespaces, cgroups) to set up the container's isolated environment;
  - executes the actual containerized process.

**Table:**

| **Aspect**          | **containerd**                                                                                  | **runc**                                                                                        |
|---------------------|-------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------|
| **Role**            | a **high-level container runtime** that manages the overall lifecycle of containers.            | a **low-level runtime** that directly sets up and runs containers using system calls.           |
| **Functionality**   | handles tasks like image pulling, storage management, networking, and container supervision.    | performs the actual container creation using Linux kernel features like namespaces and cgroups. |
| **Scope**           | broader responsibility, managing multiple containers and integrating with orchestration tools.  | narrow focus on creating and running individual containers.                                     |
| **Interaction**     | acts as an intermediary between higher-level tools (e.g., Docker Swarm, Kubernetes) and `runc`. | invoked by `containerd` or similar tools to execute the low-level container setup.              |
| **Technology Used** | coordinates container-related tasks at a higher level.                                          | directly interacts with the Linux kernel.                                                       |
| **Standards**       | implements the **Container Runtime Interface (CRI)** and integrates with Kubernetes.            | implements the **OCI runtime specification** for container execution.                           |
