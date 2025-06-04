# Each Layer is Read-Only

- layers are **read-only**, once a layer is created, it cannot be modified;
- the layers stack on top of each other to form the final image;
- for example:
  - when you add a file to the image (with `COPY` command in `Dockerfile` for example), you are not modifying the previous layer adding a file;
  - but you are creating a new snapshot of the image containing all the previous layers + the new added file.


- the only writable layer is the one on top, created when a container is instantiated from the image (see [here](../with-container/with_container.md)). 
