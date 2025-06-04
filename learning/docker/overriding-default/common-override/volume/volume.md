# Volumes

- consider we have an image defined like this:

    ```dockerfile
    FROM ubuntu
    RUN mkdir /data && echo "Important file" > /data/info.txt
    VOLUME /data
    ```

- when you start a container without manually specifying anything for `/data`, Docker will automatically create an anonymous volume for it;
- you can override the anonymous volume during container creation by manually specifying your own mount:

    ```bash
    docker run -v /my/local/dir:/data my-image
    ```


- this prevents Docker from creating the anonymous volume and instead mounts your specified local path (`/my/local/dir`) onto `/data` inside the container.
