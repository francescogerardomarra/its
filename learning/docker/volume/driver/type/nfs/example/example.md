# Example

- see what is an `nfs` driver [here](../definition/definition.md);
- example using the `--mount` flag (**recommended**):

    ```commandline
    docker run -d \
      --name my-container \
      --mount type=volume,source=my_nfs_volume,target=/data,volume-driver=local,volume-opt=type=nfs,volume-opt=o=addr=192.168.1.100,nolock,volume-opt=device=:/export/data \
      my-image
    ```

- breakdown:
  - `type=volume`: you're using a Docker-managed volume;
  - `source=my_nfs_volume`: the name of the Docker volume;
  - `target=/data`: path inside the container;
  - `volume-driver=local`: using Docker's local driver;
  - `volume-opt=type=nfs`: specifies NFS;
  - `volume-opt=o=addr=<NFS server IP>,nolock`: mount options passed to the NFS client, in this case, the server address and a flag to disable file locking;
  - `volume-opt=device=:/export/data`: path on the NFS server (we're using a specific folder from the NFS server as a Docker volume).
