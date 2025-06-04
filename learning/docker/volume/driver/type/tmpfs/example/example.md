# Example

- you can mount a `tmpfs` volume using:

    ```commandline
    docker run -d \
      --tmpfs /app/cache \
      my-image
    ```

- this command creates a `tmpfs` mount at `/app/cache` inside the container;
- you can also specify options like the mount access mode (`rw`) and the size (`64m`):

    ```commandline
    docker run -d \
      --tmpfs /app/cache:rw,size=64m \
      my-image
    ```
