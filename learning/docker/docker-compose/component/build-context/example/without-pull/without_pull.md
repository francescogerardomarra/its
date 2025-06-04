# Without `pull_policy: never`

- in this chapter, we explain why we used the `pull_policy: never` setting within the [previous](../definition/definition.md) example:
- without `pull_policy: never` Docker Compose will follow the **default pull behavior**, which is:
  - try to pull the image (`myapp:latest`) first and **only build it** if:
      - it’s not found locally;
      - and it’s not found in the remote registry.
- if the image is build, docker will tag the built image as `myapp:latest` (based on the `image:` field);


- this default behavior is equivalent to:

    ```yaml
    pull_policy: missing
    ```
