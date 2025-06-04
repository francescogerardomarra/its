# Build Context

- `Dockerfile` build context is the directory that Docker uses to find files referenced by the `COPY` and `ADD` instructions;
- during the build process, Docker sends the entire context to the Docker daemon;
- the context can be the current directory (`.`) or a specific subdirectory;


- it is crucial to minimize the context size by using a [.dockerignore](../dockerignore-file/example/example.md) file to exclude unnecessary files and directories;
- large contexts can slow down the build process and consume more resources;
- relative paths specified in `COPY` and `ADD` instructions are interpreted as relative to the context directory;
 

- if the context directory is too large, it can significantly impact performance, especially when running builds frequently.
