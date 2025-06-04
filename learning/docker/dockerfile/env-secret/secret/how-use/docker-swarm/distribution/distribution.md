# Secret Distribution

- when you deploy a service and reference a secret, Docker **only sends that secret to the nodes** where that service runs;
- the secret is made available **in memory only** and is **not persisted to disk** on worker nodes;
- it's mounted into the container as an **in-memory file** at `/run/secrets/<secret_name>`.
