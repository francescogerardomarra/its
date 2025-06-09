# Optimizing Cache Definition

- the order of `Dockerfile` commands is highly important for caching mechanism;
- **key rule**: put the least frequently changing instructions at the top and the most frequently changing ones at the bottom of the `Dockerfile`.