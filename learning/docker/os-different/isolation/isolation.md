# Isolation via Namespaces and Cgroups

- **namespaces** provide process isolation, making it appear as if the processes in the container have their own filesystem, network stack, and process table;
- **control groups (cgroups)** control resource allocation, ensuring that a container's processes don't interfere with others on the host.
