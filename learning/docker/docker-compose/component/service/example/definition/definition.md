# Example Definition

- in this example, we are going to define a stack of 3 services: 
  - `app`: 
    - a service from a custom image, that runs a simple `Hello World` Java program;
    - the image is built directly by Docker Compose.
  - `db`: a service from MySQL image;
  - `redis`: a service from Redis image (Redis is a fast in-memory database and cache).
- all the services are present just to show how to create a Docker Compose stack, **they don't communicate to each other**.
