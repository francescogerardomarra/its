# Build

- **build an image from a `Dockerfile` in the current directory**:

    ```commandline
    docker build -t <repository>:<tag> .
    ```  

- **build an image using a specific `Dockerfile` not in the current directory**:

    ```commandline
    docker build -f <path_to_dockerfile> -t <repository>:<tag> .
    ```
  
- **build an image without using any cached layers**:
  - ensures a fresh build: using the `--no-cache` flag forces Docker to rebuild each layer from scratch, ignoring any previously cached layers;
  - this is useful for ensuring that the latest versions of all dependencies are included;
  - debugging build issues: this approach can help identify issues in the build process that may be caused by outdated or incorrect cached layers.

      ```commandline
      docker build --no-cache -t <repository>:<tag> .
      ```  
  

- **pass build arguments during the image build process**:
  - customize the build process: the `--build-arg` flag allows you to pass dynamic values to the `Dockerfile` during the build process;
  - enabling greater flexibility (e.g., setting environment-specific variables like API keys or version numbers);
  - simplify `Dockerfile` maintenance: by externalizing certain values as build arguments, you can reduce hardcoding in the `Dockerfile`;
  - making it easier to update or reuse the `Dockerfile` for different configurations.

      ```commandline
      docker build --build-arg <key>=<value> -t <repository>:<tag> .
      ```
