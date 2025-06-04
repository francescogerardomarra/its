# Where Are Layers Stored?

- Docker stores layers in `/var/lib/docker/overlay2` on your machine (or similar depending on storage driver);
- each image is just a list of layer hashes;
- when a container runs, Docker stacks those layers using Union FS;
 

- Union FS is a type of filesystem that merges multiple directories (layers) into a single, unified view.