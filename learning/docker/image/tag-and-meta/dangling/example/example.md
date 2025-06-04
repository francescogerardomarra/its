# Example

In this chapter, we show an example of how a dangling image is created:

1. create a Dockerfile:

    ```dockerfile
    FROM ubuntu:latest
    RUN apt-get update && apt-get install -y python3
    ```
2. build the image and tag it `test-image:1.0`:

    ```commandline
    docker build -t test-image:1.0 .
    ```
3. show the image with `docker images` command:

    ```commandline
    REPOSITORY    TAG       IMAGE ID       CREATED         SIZE
    test-image    1.0       3a00278b619f   3 hours ago     163MB
    ```
4. edit the Dockerfile (if you rebuild the previous Dockerfile without editing it, no new image will be created because an existing one is already available):

    ```dockerfile
    FROM ubuntu:latest
    RUN apt-get update && apt-get install -y python3
    EXPOSE 8080 # new line added
    ```
5. build the image and tag it again as `test-image:1.0`:

    ```commandline
    docker build -t test-image:1.0 .
    ```
6. show the image with `docker images` command:

    ```commandline
    REPOSITORY    TAG       IMAGE ID       CREATED         SIZE
    <none>        <none>    3a00278b619f   3 hours ago     163MB
    test-image    1.0       9cd8c7eb7c31   3 hours ago     163MB
    ```
7. as you can see the `test-image:1.0` tag is moved to the new `9cd8c7eb7c31` image, making the old `3a00278b619f` as a dangling image, since it has no other tags and containers associated.
