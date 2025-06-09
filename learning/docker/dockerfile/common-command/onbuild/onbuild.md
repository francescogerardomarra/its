# `ONBUILD`

- **purpose:**
  - defines a **trigger** that executes a command **only when the image is used as a base image** in another Docker build;
  - useful for creating **parent images** that automatically apply build steps when extended;
  - helps enforce **consistent build steps** in child images without modifying their `Dockerfile`.

- **syntax:**

    ```dockerfile
    ONBUILD <Dockerfile instruction>
    ```
  
    (works with `COPY`, `RUN`, `ADD`, `WORKDIR`, etc., but not with `FROM` or `ONBUILD` itself.)

- **example:**
  - **automatically copy application files when the image is used as a base:**
  
    ```dockerfile
    FROM python:3.9
    ONBUILD COPY . /app
    ONBUILD WORKDIR /app
    ```
  - **use this image as a base in another Dockerfile:**
  
    ```dockerfile
    FROM my-python-base
    ```
    
    this automatically triggers the **ONBUILD** commands from `my-python-base`.

  - **check `ONBUILD` triggers in an image:**
    ```sh
    docker inspect my-python-base | grep OnBuild
    ```
