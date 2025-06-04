# User Space

- the OS inside a container (as it appears) is defined by the **user space**, which includes things like libraries, binaries, and configurations;
- a container image can package a completely different user space;
- for example, you might run a container with an Ubuntu user space on a host running Fedora, or even a minimal Alpine Linux container on a Debian host.
