# `volume_name:ro or :rw`

- `volume_name:ro` indicates that the volume is mounted in **read-only** mode;
- when a volume is mounted as `ro`, the container can **read** the data from the volume but cannot **modify** or **write** to it;
- this is useful when you want to prevent the container from altering the contents of the volume, such as in cases where data integrity needs to be preserved;
 

- `volume_name:rw` indicates that the volume is mounted in **read-write** mode;
- when a volume is mounted as `rw`, the container can **read** and **write** to the volume, allowing for modification of the files inside it;
- `:rw` is the default access mode if no mode is specified;
 

- using `:rw` gives the container full access to the volume, which is helpful when the container needs to create, update, or delete files in the volume.

**Examples:**

- `ro` volume:

    ```yaml
    version: '3'
    services:
      my_service:
        image: some_image
        volumes:
          - my_volume:/data:ro
    
    volumes:
      my_volume:
    ```

- `rw` volume:

    ```yaml
    version: '3'
    services:
      my_service:
        image: some_image
        volumes:
          - my_volume:/data:rw
    
    volumes:
      my_volume:
    ```
