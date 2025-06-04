# Limitations

- a container cannot use a kernel incompatible with the host (e.g., you can't run Windows-native containers on a Linux host directly without emulation);
- the container's apparent "OS" is limited to what its user space environment supports, and it still depends on the host kernel's capabilities;
- if a container is running an image built for a newer version of Linux that relies on kernel features absent in the host, those features won't work;


- if the host kernel does not support a certain file system or system call, the container won't be able to use it, even if the user space inside the container expects it.
