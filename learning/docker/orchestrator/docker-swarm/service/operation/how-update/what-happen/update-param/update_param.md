# Respecting Update Parameters

If you provided flags like `--update-parallelism`, `--update-delay`, etc., Docker honors those settings: <!-- todo: link to rolling updates chapter -->
- `--update-parallelism` controls how many containers update at once;
- `--update-delay` sets a wait time between update batches;
- `--update-failure-action` can dictate what to do on failure (`pause`, `continue`, or `rollback`).
