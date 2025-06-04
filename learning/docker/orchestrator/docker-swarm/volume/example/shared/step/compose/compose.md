# `docker-compose.yml` file

- use the following`docker-compose.yml` file:

    ```yaml
    version: "3.9"
    
    services:
      web:
        image: nginx
        volumes:
          - shared-volume:/usr/share/nginx/html
        deploy:
          replicas: 3
    
    volumes:
      shared-volume:
        driver: local
        driver_opts:
          type: "nfs"
          o: "addr=192.168.1.100,nolock,soft,rw"
          device: ":/mnt/shared_data"
    ```

- replace `192.168.1.100` and `:/mnt/shared_data` with your actual NFS server and path.

**Above `docker-compose.yml` file explanation:**

- the YAML defines a Docker Compose configuration using version 3.9 of the Compose file format;
- it sets up a service named `web` that uses the `nginx` image;
- the service mounts a volume named `shared-volume` to the container path `/usr/share/nginx/html`, which is where Nginx serves website files from;


- the `deploy` section specifies that three replicas of the `web` service should run, enabling load balancing or redundancy;
- the `shared-volume` is defined under the `volumes` section using the `local` driver with custom options to mount an NFS share;
- it is specified as `local` because Docker does not have a built-in `shared` volume driver, and the `local` driver is used as a base to configure external mounts like NFS through `driver_opts`;
 

- the `driver_opts` specify that the volume is of type "nfs", with NFS options such as `nolock`, `soft`, and `rw` for read-write access;
- the `addr=192.168.1.100` option points to the IP address of the NFS server;
- the `device=:/mnt/shared_data` indicates the [exported path](../nfs-server/nfs_server.md) on the NFS server that will be mounted inside the containers.
