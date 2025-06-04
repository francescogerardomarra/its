# `local`

- the `local` driver is the default Docker volume driver used when you create a volume without specifying a driver explicitly;
- it stores data on the local filesystem of the Docker host where the container is running;
- volumes created with the `local` driver are stored in the host’s filesystem, usually under:

    ```commandline
    /var/lib/docker/volumes/<volume-name>/_data/
    ```


- you can create volumes using the `local` driver in these ways:
  - [manually](../../../create/manually/manually.md);
  - automatically:
    - [Dockerfile](../../../create/automatically/dockerfile/dockerfile.md);
    - [container inialitization](../../../create/automatically/container/container.md);
    - [`docker-compose.yml` file](../../../create/automatically/compose/compose.md).
