# Key Caching Strategies

- **order matters**: Docker caches each layer, if a layer changes, all subsequent layers must be rebuilt;
- **minimize changes in early layers**: keep frequently changing commands at the bottom;
- **separate dependencies from application code**: install dependencies first so they are cached separately from frequently changing files.
