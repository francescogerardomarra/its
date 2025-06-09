# Example

- this runs a Nginx container:

    ```commandline
    docker run --network host nginx
    ```
- but instead of being accessible on a container-specific IP, it will serve content directly via the host’s IP;
- if Nginx listens on port `80` inside the container, it will be exposed on port `80` of the host.
