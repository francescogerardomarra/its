# Characteristics

- **no tag**: dangling images appear as <none> for both the [repository and tag](../../what-is-tag/what_is_tag.md) when you list images using docker images;

    example:

    ```commandline
    REPOSITORY    TAG       IMAGE ID       CREATED         SIZE
    <none>        <none>    abc123def456   2 weeks ago     123MB
    ```

- **unused**: these images are not associated with any running or stopped container;
- **disk space consumption**: even though they are unused, they still consume disk space.
