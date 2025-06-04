# Pushing and Pulling

- **authenticate with a Docker registry before pushing or pulling images**:
  - see the ["push the tag to repository"](../../tag-and-meta/push-repo/push_repo.md) chapter to see more info:

      ```commandline
      docker login
      ```

- **log out from a Docker registry**:

    ```commandline
    docker logout
    ```

- **pull an image from a Docker registry**:

    ```commandline
    docker pull <repository>:<tag>
    ```

- **push an image to a Docker registry**:

    ```commandline
    docker push <repository>:<tag>
    ```
