# Respecting Update Parameters

If you provided flags like `--update-parallelism`, `--update-delay`, etc., (see [here](../../../../../node/why-multiple/service-deployment/example/example.md)) Docker honors those settings:
- `--update-parallelism` controls how many containers update at once;
- `--update-delay` sets a wait time between update batches;
- `--update-failure-action` can dictate what to do on failure (`pause`, `continue`, or `rollback`).
