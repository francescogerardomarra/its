### 3. **Image Pulling Cache**

If you use pre-built images (from Docker Hub or a registry), Compose checks if the image is already available locally:

- if it’s there and matches the tag, it reuses it;
- use `docker compose pull` to force updating from the registry.
