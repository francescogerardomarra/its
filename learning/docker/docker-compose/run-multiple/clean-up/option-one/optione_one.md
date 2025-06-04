# Option 1

Sometimes Docker won’t even replace the running container unless you stop or remove it; to be safe:

```commandline
docker compose down
docker compose up -d --build
```

- `docker compose down`:
    - stops and removes containers, networks, and default volumes (but not named volumes unless you use --volumes).
- `docker compose up -d --build`:
    - rebuilds the image (if needed);
    - starts fresh containers.

**Use when:**

- you want a clean slate (e.g., resetting database volumes);
- you want to remove unused containers/networks;
- you want to ensure no old config or data is lingering.
