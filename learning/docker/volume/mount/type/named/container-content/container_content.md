# What Happens to Container Folder Content?

**Consider this scenario:**

- you want to create a named volume `my-volume` and mount it to the `/data` container folder;
- the `/data` folder already has some content;
- **what will happen to that content after the volume mount?**

**Answer:**

- if `my_volume` is **empty**:
  - Docker copies the existing contents of the container's `/data` into the new `my_volume`.

- if `my_volume` is **not empty**:
  - it behaves like [bind mounts](../../bind/container-content/container_content.md).

**Example (`my_volume` is **empty**):**

- consider that `/data` folder has some files within `my-image`;
- `my_volume` does not exist yet, so Docker automatically creates it when the container starts;
- command:

    ```commandline
    docker run -v my_volume:/data my-image
    ```
  

- content of `/data` is copied into `my_volume`, and the data will persist even if the container stops or is removed.
