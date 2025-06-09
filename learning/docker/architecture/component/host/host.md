# Docker Host

The docker host is composed of these components:

- **Docker Daemon (dockerd)**:
  - runs on the host machine;
  - manages Docker objects such as images, containers, networks, and volumes;
  - listens for requests from the Docker client.
- **container runtime**:
  - responsible for creating and running containers;
  - uses low-level container tools like `containerd` and `runc`.
- **images**:
  - immutable snapshots containing application code, runtime, libraries, and dependencies.


- **containers**:
  - lightweight, standalone, and portable execution environments;
  - created from images and isolated from the host and other containers.
- **storage**:
  - handles file systems for images and containers;
  - supports multiple drivers (e.g., overlay2, aufs).
