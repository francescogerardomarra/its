# Driver

- the `driver` option within the `volumes` section specifies which volume driver to use;
- a volume driver allows integration with external storage systems, such as NFS or cloud-based storage solutions;
- the default driver in Docker is `local`, which stores volume data on the host filesystem;
- you can specify a different driver by adding a `driver` key under the volume name, like this:
  ```yaml
  volumes:
    myvolume:
      driver: local
  ```  
- some drivers may require additional options to be set using the `driver_opts` key;
- network File System (NFS) is a distributed file system protocol that allows a user on a client computer to access files over a network in the same way they would access local storage;
- it enables file sharing between multiple machines, making it useful for sharing data between containers or between a host and its containers.
- for example, to use an NFS volume, you might configure it as follows:
  ```yaml
  volumes:
    myvolume:
      driver: local
      driver_opts:
        type: nfs
        o: addr=192.168.1.100,nolock,soft,rw
        device: ":/path/to/nfs"
  ```  
- when using third-party or custom volume drivers, you may need to install plugins or additional packages on the host system.
