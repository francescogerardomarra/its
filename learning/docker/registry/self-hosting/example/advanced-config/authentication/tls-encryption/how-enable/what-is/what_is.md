# What Is The `config.yml`

- the `config.yml` file is a YAML-based configuration file used by the Docker registry to define its runtime behavior; 
- it contains settings for:
  - **HTTP/TLS**: defines how the registry serves requests, including TLS encryption;
  - **storage**: 
    - specifies where images are stored (e.g., local filesystem, Amazon S3, etc.);
    - additionally, the registry can be configured to act as a pull-through cache;
    - in this setup, the registry fetches images from an upstream registry (like Docker Hub) on demand and caches them locally;
    - this reduces repeated downloads from the upstream registry, improves performance, and conserves bandwidth in environments with multiple Docker hosts pulling the same images.
  - **authentication**: configures how users authenticate with the registry;
  - **logging**: sets up logging for tracking registry activity;
- without the proper configuration in `config.yml`, the registry will not be able to enable secure HTTPS communication;


- this file is essential for customizing and securing your Docker registry.
