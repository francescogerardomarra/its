# Comparison

**Non-optimized:**

- see the example [here](../non-optimized/non_optimized.md);
- build command:

    ```bash
    docker build -t myapp .
    # Every change forces a full re-download of dependencies and a complete rebuild.
    ```
- build process:
  - **slow**: takes a long time even for small code changes.

**Optimized:**

- see the example [here](../optimized/optimized.md);
- build command:

    ```bash
    docker build -t myapp .
    # Dependencies are cached unless pom.xml changes, speeding up builds.
    ```

- build process:
  - **fast**: cached dependencies make rebuilds much quicker.
