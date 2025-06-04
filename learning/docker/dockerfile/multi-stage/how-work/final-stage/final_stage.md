# Final Stage

- **minimal final image**:  
  - the last `FROM` instruction defines the final image that will be built;
  - it typically uses a lightweight base image (e.g., `alpine` or `distroless`) and includes only the files required to run the application.
- **why use multistage builds?**  
  - the earlier stages are used to build the application (e.g., compiling code, running tests), but these stages are discarded, leaving only the runtime environment in the final image;
  - this ensures:
    - the image is smaller and lightweight;
    - only the necessary files (e.g., binaries) are included;
    - build tools, source code, and temporary files are excluded.
