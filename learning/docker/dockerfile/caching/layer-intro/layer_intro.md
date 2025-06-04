# Introduction to Docker layers:

- Docker layers are read-only, stacked filesystems that compose a Docker image, where each layer represents a change or instruction (like adding a file or installing software) from the `Dockerfile`;
- layers are cached and shared between images, allowing for efficient storage and reuse, as only modified layers are updated when rebuilding an image;
- the topmost layer is writable and represents the container's runtime state, while lower layers are immutable;


- not all `Dockerfile` commands create layers, some, like `WORKDIR`, `CMD`, `LABEL`, and `ENV`, only update metadata or set configuration without generating new filesystem layers;
- these commands do not impact the image size or caching in the same way as file or package additions;
- see more info to [layers]() chapter. // TODO: link to layers chapter index

