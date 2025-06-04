# Inspect

- **view detailed information about an image**:
  - this command provides detailed information about the image, such as its layers, size, creation date, and the `Dockerfile` instructions used to build it (e.g., `CMD`, `ENTRYPOINT`, `ENV`).

      ```commandline
      docker image inspect <repository>:<tag>
      ```  

  **output**:

  ```commandline
  docker image inspect base-app:latest 
  
  [
      {
          "Id": "sha256:6bd3c7243d906ece66b818c2c016db89fe5ca1c4685288ed2e6d6373976a96ae",
          "RepoTags": [
              "base-app:latest"
          ],
          "RepoDigests": [],
          "Parent": "",
          "Comment": "buildkit.dockerfile.v0",
          "Created": "2025-01-10T17:36:08.085292107+01:00",
          "DockerVersion": "",
          "Author": "",
          "Config": {
              "Hostname": "",
              "Domainname": "",
              "User": "",
              "AttachStdin": false,
              "AttachStdout": false,
              "AttachStderr": false,
              "ExposedPorts": {
                  "8086/tcp": {}
              },
              "Tty": false,
              "OpenStdin": false,
              "StdinOnce": false,
  
  ... and more...
  ```

- **show the history of an image (layers and commands)**:
  - this command displays the history of the image, including each command executed in the `Dockerfile` (e.g., `RUN`, `COPY`, `ADD`) and the corresponding layers created during the build;
  - it provides the size of each layer, helping you identify specific steps in the build process that may have contributed to a large image size, which is useful for optimizing the image;
  - by reviewing the sequence of commands and changes made to the image, you can pinpoint problematic layers or identify where unexpected changes may have been introduced during the build.

      ```commandline
      docker history <image_name>:<tag>
      ```
  
  **output** (`Dockerfile` used to build `base-app:latest` image [here](../../tag-and-meta/dangling/example/example.md) at **step 4**):

  ```commandline
  docker history base-app:latest
  
  IMAGE          CREATED         CREATED BY                                      SIZE      COMMENT
  6bd3c7243d90   4 minutes ago   EXPOSE map[8080/tcp:{}]                         0B        buildkit.dockerfile.v0
  <missing>      4 minutes ago   RUN /bin/sh -c apt-get update && apt-get ins…   85.2MB    buildkit.dockerfile.v0
  <missing>      7 weeks ago     /bin/sh -c #(nop)  CMD ["/bin/bash"]            0B        
  <missing>      7 weeks ago     /bin/sh -c #(nop) ADD file:bcebbf0fddcba5b86…   78.1MB    
  <missing>      7 weeks ago     /bin/sh -c #(nop)  LABEL org.opencontainers.…   0B        
  <missing>      7 weeks ago     /bin/sh -c #(nop)  LABEL org.opencontainers.…   0B        
  <missing>      7 weeks ago     /bin/sh -c #(nop)  ARG LAUNCHPAD_BUILD_ARCH     0B        
  <missing>      7 weeks ago     /bin/sh -c #(nop)  ARG RELEASE                  0B 
  ```
