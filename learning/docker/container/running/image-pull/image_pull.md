# Pull the Image

- first, pull the image from a Docker registry, such as Docker Hub;

- for example (replace `<image_name>` with the name of the image you want to use, such as `nginx`, `ubuntu`, or `myrepo/myimage:tag`):

    ```bash
    docker pull <image_name>
    ```

- this command:

  - checks for the image locally: Docker first checks if the specified image (`<image_name>`) is already available locally on your machine;
  - if the image exists locally with the same tag, Docker skips the download;
  - contacts the registry: if the image isn't available locally or is outdated, Docker connects to the specified registry (default is Docker Hub);
  - downloads the image layers:
    - Docker images are made up of multiple layers;
    - the command downloads all the layers that are missing locally;
    - if some layers already exist on your machine (e.g., from a similar image), Docker reuses those layers to save bandwidth;
    - stores the image locally: once all layers are downloaded, Docker assembles the image and stores it in your local Docker environment for future use.
