# Lifetime

- all mounts (bind, volume, anonymous) exist during container runtime;
- when the container stops, the mounts are removed;
- the mounts are automatically reattached when the container starts again;


- **so even if, when the container is stopped, the mount is removed, the association between the container and the volume still exists;**
- when the container is stopped or removed:
  - **bind mount**: the host folder content remains;
  - **named/anonymous volume**: the volume content still persists.
