# Dockerfile

- in a `Dockerfile`, you can specify a `VOLUME` instruction:

    ```dockerfile
    VOLUME /data
    ```
- during the build, this instruction just marks `/data` as a volume location; it doesn't create a volume yet;
- when a container is run from this image (using `docker run`), Docker sees that `/data` should be a volume;


- automatically, Docker:
  - creates an anonymous volume (if you didn't explicitly mount anything to `/data`);
  - mounts that volume to the `/data` path inside the container.
