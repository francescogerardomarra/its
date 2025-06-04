# `FROM`

- **purpose:**
  - specifies the **base image** from which the container will be built;
  - can reference official images, custom images, or specific versions (tags);
  - the base image defines what will be inside the container, including system libraries, utilities, and pre-installed software, but it does not contain a kernel, it relies on the host’s kernel;
  - for example, a base image can contain a full Linux user space (like Ubuntu or Debian) with system tools like bash and apt, or it can be a minimal image (like Alpine) with only core utilities;
  - some base images also include pre-installed software, like python:3.9 (which includes Python) or node:16 (which includes Node.js and npm).
- **syntax:**

    ```dockerfile
    FROM <image>:<tag>
    ```
    
    (default tag is `latest` if not specified).

- **example:**
  - use the latest Ubuntu as the base image:
    ```dockerfile
    FROM ubuntu:latest
    ```
  - use a specific Node.js version:
    ```dockerfile
    FROM node:16
    ```
  - use a custom image as a base:
    ```dockerfile
    FROM my-custom-image:v1.2
    ```  
