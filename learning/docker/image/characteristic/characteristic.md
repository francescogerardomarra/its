# Characteristics

- **immutable**: 
  - Docker images are unchangeable once created;
  - any modifications result in a new image.
- **layered file system**:
    - images are built in layers;
    - each layer corresponds to a specific instruction in the Dockerfile (e.g., `RUN`, `COPY`, `ADD`);
    - layers are cached to speed up the build process and reduce storage requirements.
- **portable**: images can run on any system with Docker installed, ensuring consistency across different environments (e.g., development, testing, production);
 

- **repository-managed**: images can be stored and shared using repositories, such as Docker Hub or private registries.
