# Example

Consider running a container built from an Ubuntu image on a Fedora host:

1. the container shares the Fedora kernel but uses the Ubuntu user space;
2. when a program inside the container makes a system call, it is handled by the Fedora kernel;
3. however, the libraries and binaries the program uses are from Ubuntu.
