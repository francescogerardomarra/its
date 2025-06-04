# How to Make It Rebuild

**Option 1:**

Manually trigger rebuild:

```bash
docker compose build
docker compose up -d
```

**Option 2:**

Combine in one step:

```bash
docker compose up -d --build
```

This will:
- force a rebuild of the image (so Docker picks up Dockerfile changes);
- restart the container with the **new image**.
