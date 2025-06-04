# How Caching Works

Consider a scenario where we are building the same image for the second time:

1. Docker reads the `Dockerfile` line by line;
2. for each instruction, it checks:
   - has this instruction changed?
   - has any file or content used in this instruction changed?
3. if nothing has changed, Docker uses the cached version;
4. if something changes, Docker invalidates the cache for that step and all following steps.

Each Dockerfile step creates a new layer that represents a change to the image’s filesystem.

Later layers depend on the exact state of the layers below them.

So even if a later step hasn’t changed, it can’t be reused if the layers it builds on have changed — because it would now be applied on top of a different base.