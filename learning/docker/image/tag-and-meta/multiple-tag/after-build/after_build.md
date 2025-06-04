# Tagging After Build

- if the image is already built, you can use the `docker tag` command to add additional tags to it;
- this assumes `my-image:latest` already exists;
- now, `my-image:1.0.0` and `my-image:staging` will also point to the same image:

    ```bash
    docker tag my-image:latest my-image:1.0.0
    docker tag my-image:latest my-image:staging
    ```

- instead of `my-image:latest`, to refer to the image, you could use the IMAGE ID.
