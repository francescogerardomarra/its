# Image Without `Dockerfile`

- it is possible to create a Docker image without a `Dockerfile`; 
- while using a `Dockerfile` is the most common and structured way to define how an image is built, there are alternative approaches;
- we will not delve into the alternatives, since using a `Dockerfile` is generally preferred for its consistency, transparency, and version control benefits;
 

- these alternative methods can be handy for specific scenarios, such as quick prototyping, automation, or advanced use cases:
  - [Docker CLI (`docker commit`)](../../container/common-command/checkpointing/commit/commit.md):
    - you can create a container from an image, make modifications to it, and then use `docker commit` to save those changes as a new image.
  - Docker BuildKit with inline commands:
    - Docker BuildKit allows building images using shell-like commands without a `Dockerfile`.
  - Docker API or SDK:
    - you can use Docker's API or SDKs (available for languages like Python, Go, and Node.js) to programmatically create images.
  - build tools like Packer:
    - Packer automates the process of creating Docker images by defining and executing configuration steps in a single file, ensuring consistency and efficiency.
