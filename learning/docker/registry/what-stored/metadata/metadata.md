# Images Metadata

- **manifest files:** 
  - a manifest describes the image, including the layers that make it up and the associated tags;
  - a manifest provides details about the architecture of the image (e.g., `amd64`, `arm64`) and the operating system it is compatible with (e.g., `linux`, `windows`);
  - it ensures that the correct image layers are pulled and assembled in the correct order when the image is downloaded, making it crucial for reproducibility and consistency.

- **configuration files:** 
  - these files store metadata about how to run the container, such as environment variables, default commands, and exposed ports;
  - configuration files include the image's entry point and command instructions, determining the initial process that runs when a container is started;
  - they also define any default values for environment variables, which can be overridden at runtime to customize the container’s behavior.
