# Optimize Build Performance

- Docker caches each stage separately, allowing efficient reuse of unchanged layers in subsequent builds;
- this speeds up builds, especially when only specific stages need to be rebuilt.
