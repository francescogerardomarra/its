# Multiple Stages

- **stages and `FROM` instruction**:
  - each stage begins with a `FROM` instruction, which sets the base image for that stage;
  - multiple `FROM` instructions can be included in the same `Dockerfile`;
  - each stage in a multistage build creates a separate intermediate image, but these images are not directly saved or used unless explicitly specified (except for the final image).
- **stages are executed in order**:  
  - stages are executed sequentially, from top to bottom;
  - they cannot be executed in parallel, as subsequent stages may depend on artifacts produced by earlier ones.
- **artifacts can be shared across stages**:  
  - while each stage is independent in terms of its file system and environment, you can copy files or artifacts (e.g., binaries, compiled code) from one stage to another using `COPY --from`.
