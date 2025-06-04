# How To List Them?

- you can list dangling images specifically using the following command:

    ```commandline
    docker images -f "dangling=true"
    ```
- Docker's filtering logic for `dangling=true` simply looks for untagged images;
- it doesn't check whether the image is referenced by a container;


- **so it will show also images that are not dangling, for example, images that have no tag but a container associated with them**;
- the images without tag that you see running the `docker images` or the above command are dangling only if they have no running or stopped container associated with them.
