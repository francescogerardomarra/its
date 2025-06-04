# Option 2

If you just want to rebuild and restart everything cleanly:

```commandline
docker compose up -d --force-recreate --build
```

- rebuilds the image;
- replaces running containers with new ones;
- keeps existing volumes and networks;


- doesn’t stop services you removed from the Compose file unless you use `--remove-orphans`.

**Use when:**

- you want to rebuild and restart all services with no downtime (ish);
- you want to keep existing data/volumes intact;
- you don't want to destroy networks or external dependencies.
