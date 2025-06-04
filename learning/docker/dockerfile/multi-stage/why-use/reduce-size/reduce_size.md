# Reduce Image Size

- in a traditional Docker build, all dependencies, compilers, and build tools remain in the final image, making it large;
- multi-stage builds allow you to use an intermediate container for building and only copy the necessary artifacts to the final image, significantly reducing its size.
