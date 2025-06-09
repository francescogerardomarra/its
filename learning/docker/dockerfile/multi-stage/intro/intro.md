# Multi-Stage Builds Introduction

- a **multi-stage build** in a `Dockerfile` is a technique that allows you to use multiple `FROM` statements in a single `Dockerfile`, creating separate stages within the build process;
- this approach is primarily used to optimize the size and efficiency of the final Docker image, by separating the build environment from the runtime environment;
- **example**:
  - it allows creating **stage 1**, which uses a temporary, heavy image containing the full JDK to compile the Java program and generate the `.jar` file;
  - within the same `Dockerfile`, it allows creating **stage 2**, which uses a lightweight image to distribute the application;
  - this image contains only the JRE (the Java runtime environment, which is the "java runner" and does not include the compiler), and it retrieves the `.jar` file generated in **stage 1**;
  - more **stages** can be present, this was just an example.
