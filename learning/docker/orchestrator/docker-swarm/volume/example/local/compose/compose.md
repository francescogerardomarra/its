# Docker Compose

- you can define volumes in your service in a **Docker Compose file** with version 3 or higher:

    ```yaml
    version: "3.9"
    services:
      web:
        image: nginx
        volumes:
          - myvolume:/data
    
    volumes:
      myvolume:
    ```

- it defines a Docker Compose file using version 3.9, which supports deployment in Docker Swarm mode;
- it declares a service named `web` that uses the official `nginx` image;
 

- it mounts a named volume `myvolume` into the container at the path `/data`;
- it ensures that data written by the Nginx container to `/data` is stored in the named volume `myvolume`;
- it defines the named volume `myvolume` under the `volumes` section, making it available for service use;
 

- it creates the volume `myvolume` only on the node where the container is scheduled to run, meaning it is local to that node and not automatically shared across nodes in the swarm;
- it implies that if the service is redeployed or scaled and lands on a different node, a different volume instance named `myvolume` will be created on that node;
- leading to data not being shared between nodes, unless an external volume driver (like [NFS](../../../type/shared/shared.md) or a plugin like [Portworx or RexRay](../../../type/plugin/definition/definition.md)) is used.
