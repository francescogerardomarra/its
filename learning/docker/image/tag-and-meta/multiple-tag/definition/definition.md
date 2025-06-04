# Multiple Image Tags

- it is possible to assign multiple tags to a Docker image;
- tags are essentially aliases for a given image ID, and a single image can have multiple tags pointing to it;
- this is often done to provide versioning or distinguish between different environments (e.g., `latest`, `staging`, `production`);

- for example, this allows a single image to have multiple tags, such as a version tag (e.g., `1.0.0`) and an environment tag (e.g., `staging`).