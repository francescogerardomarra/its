# Unpredictable Behavior:

- if the `latest` tag gets updated to point to a different version, your builds or deployments might break unexpectedly;
- the new version of the image may introduce:
    - new features;
    - bug fixes;
    - dependency updates;
    - configuration changes.
- these changes might alter the runtime behavior of your application or conflict with your existing setup;


**Example:**

- you define a `Dockerfile` where the base image points to the `latest` tag;
- initially, `latest` points to the image with tag `2.9.1`, which uses Python 3.8 as the runtime environment;
- `latest` gets updated to point to image with tag `2.9.2`, which switches to Python 3.9;


- you need to re-build the image, for example, for a code change;
- if your application has compatibility issues with Python 3.9, it may fail unexpectedly.
