# `no_copy`

- prevents Docker from copying files from the container’s directory to the volume when the volume is created;
- by default, when a volume is created and the volume is empty, Docker copies the contents of the container’s directory into the volume;
- when the `no_copy` option is set to `true`, Docker skips this copy process, and the volume remains empty until populated manually or by the container;


- useful when you want to avoid initializing the volume with the container's files, especially if you plan to initialize it with data after the container starts;
- typically used when you want a volume that won’t have any initial content or to avoid unnecessary copy operations, which might be slow or use resources;
- does not affect existing volumes; if the volume already contains data, it will not overwrite or modify that data, regardless of whether `no_copy` is set;


- this does not happen with bind volumes, as bind mounts directly reference directories on the host filesystem, so there’s no need for Docker to copy anything;
- with bind mounts, if both the container’s directory and the host directory have files, both sets of files will be visible in the container, but the files are not copied between the two; 
- they are simply shared and synchronized.

**Example:**

```yaml
version: '3'
services:
  app:
    image: myapp
    volumes:
      - my_data:/data

volumes:
  my_data:
    no_copy: true
```
