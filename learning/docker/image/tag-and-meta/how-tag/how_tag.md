# How to Tag an Image?

- to tag an image **during a build**, add the `-t` or `--tag` flag:

    ```commandline
    docker build -t my-username/my-image .
    ```

- you can also tag an **existing image** using the `docker tag` command:

  ```commandline
  docker tag myapp:1.0 myapp:latest
  ```

- for the **previous command** you can also specify the IMAGE ID, instead of the tag (`myapp:1.0`):

  ```commandline
  docker tag <image-id> myapp:latest
  ```
