# Tagging During Build

- you can use the `-t` flag multiple times while building an image;
- this will assign all the specified tags (`1.0.0` and `staging`) to the image:

    ```commandline
    docker build -t my-image:1.0.0 -t my-image:staging .
    ```
