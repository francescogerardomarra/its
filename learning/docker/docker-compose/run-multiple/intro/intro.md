# Run Multiple `docker compose up` Introduction

In this chapter, we are going to see what happens during this common scenario:

1. We have a docker-compose.yml:

    ```yaml
    version: "3.9"
    
    services:
      app1:
        build:
          context: ./app1
          dockerfile: Dockerfile
        ports:
          - "8000:8000"
        depends_on:
          - app2
    
      app2:
        build:
          context: ./app2
          dockerfile: Dockerfile
        ports:
          - "8001:8001"
    
      redis:
        image: redis:7.2
        ports:
          - "6379:6379"
    ```
2. we launch the command `docker compose up`;
3. it will:
   1. build `app1`, `app2`;
   2. pull `redis:7.2` from registry;
   3. use the 3 images to start the containers;

**What will happen if we change the Dockerfile of `app1`, `app2` and then run `docker compose up` again? Docker Compose will rebuild the images and use them to instantiate new containers or not?**

- **no**, `docker compose up -d` **will not automatically rebuild the image** just because the Dockerfile changed.
- see the reasons within the [next](../../index.md) chapters.
