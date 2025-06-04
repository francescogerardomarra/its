# `docker compose down --rmi all`

- this command does the same of [docker compose down](../compose-down/compose_down.md), but also removes all images built or pulled by Docker Compose;
- Docker Compose builds images via `build:` directive;
- Docker Compose pulls images from registries via `image:` directive (if both `image:` and `build:` are present, `image:` is used as the **tag** name for the image that's being built).
