# Is It Possible to Instantiate Containers?

- **yes**, you can instantiate a container from a dangling image;
- since a dangling image has no repository or tag (both are `<none>`), you must reference it by its `image ID`;
- example:
  - we have the following dangling image:

    ```commandline
    REPOSITORY    TAG       IMAGE ID       CREATED         SIZE
    <none>        <none>    abc123def456   2 days ago      120MB
    ```
  - run a container using the `image ID`:

    ```commandline
    docker run -it abc123def456
    ```


- a container instantiated from a dangling image will display the `image ID` it is based on, rather than a tag;
- this makes it harder to understand the container's context;
- **best practice**: 
  - avoid dangling images to create containers;
  - always tag your images during the build process;
  - if you find a dangling image you need, tag it to make it easier to manage.

**Example:**

The following `ee7392b21e0d` container was created from a dangling image, as you can see it shows the image ID under the `IMAGE` column:

```commandline
ee7392b21e0d   088e984e67ff   "/bin/bash"              3 seconds ago        Exited (0) 3 seconds ago                                         youthful_noyce
ea51063f8fd8   base-python    "/bin/bash"              About a minute ago   Exited (0) 59 seconds ago                                        trusting_bell
```
