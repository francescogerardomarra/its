# Definition

- tagging images is the method to provide an image with a memorable name;
- however, there is a structure to the name of an image;
- a full image name has the following structure:

    ```text
    [HOST[:PORT_NUMBER]/]PATH[:TAG]
    ```

    - `HOST`: 
      - the optional registry hostname where the image is located;
      - if no host is specified, Docker's public registry at `docker.io` is used by default.
    - `PORT_NUMBER`: 
      - the registry port number if a hostname is provided;
      - for most of the host default ports are implied and do not need to be explicitly included in the image name.
    - `PATH`: 
      - the path of the image, consisting of slash-separated components;
      - for Docker Hub, the format follows `[NAMESPACE/]REPOSITORY`, where namespace is either a user's or organization's name;
      - if no namespace is specified, `library` is used, which is the namespace for Docker Official Images.
    - `TAG`: 
      - a custom, human-readable identifier that's typically used to identify different versions or variants of an image;
      - if no tag is specified, latest is used by default.

**Examples:**

| Image                                                  | Equivalent                                  | Description                                                                                                                                       |
|--------------------------------------------------------|---------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
| `nginx`                                                | `docker.io/library/nginx:latest`            | pulls an image from the `docker.io` registry, the library namespace, the `nginx` repository, and the `latest` tag.                                |
| `docker/welcome-to-docker`                             | `docker.io/docker/welcome-to-docker:latest` | pulls an image from the `docker.io` registry, the `docker` namespace, the `welcome-to-docker` repository, and the `latest` tag.                   |
| `ghcr.io/dockersamples/example-voting-app-vote:pr-311` | -                                           | pulls an image from the GitHub Container Registry, the `dockersamples` namespace, the `example-voting-app-vote` repository, and the `pr-311` tag. |
