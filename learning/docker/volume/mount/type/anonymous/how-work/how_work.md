# How It Works (With Example)

- typically, the anonymous volume is created when you define a container and mount a volume **without giving it a name**:

    ```commandline
    docker run -v /data my-image
    ```

- here, `/data` is a path inside the container;
- Docker automatically creates an anonymous volume on the host and mounts it to `/data` in the container;


- the volume name will be something like `f72d775f760fda95f401e123456789abcd1234e7f98a12f05b5f42de55123b3`;
- **if the container is removed, the anonymous volume still exists, but it's harder to reuse for other containers because you need to use the above akward name**;
- if you don't specify a name when defining a volume, Docker will create an anonymous volume automatically;


- to create an **anonymous volume**, remove the volume names from these examples and provide only the container directory path:
  - [Dockerfile](../../../../create/automatically/dockerfile/dockerfile.md);
  - [container inialitization](../../../../create/automatically/container/container.md) (the above example);
  - [`docker-compose.yml` file](../../../../create/automatically/compose/compose.md).
