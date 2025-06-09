# Remove a Tag

1. imagine you have 2 images with 2 different tags:
   - `myapp:1.0`
   - `myapp:2.0`
2. you want to add the `myapp:latest` tag to image `myapp:2.0`:

    ```commandline
    docker tag myapp:2.0 myapp:latest
    ```

   `myapp:latest` tag now points to the same image as `myapp:2.0` tag.

3. after you can remove a tag using the following command:

    ```commandline
    docker rmi myapp:latest
    ```

    - **important:** 
      - if the image has only one tag, running the `docker rmi` command **will remove both the tag **and** the image itself** (as long as no containers are using it, otherwise you'll receive an error, even if the container is stopped);
      - to avoid deleting the image, first **add another tag before removing the original one**.
    - Docker will not allow the tag `myapp:latest` to be removed if a container was created from it, even if the image has another tag (`myapp:2.0`) and even if the container is stopped.