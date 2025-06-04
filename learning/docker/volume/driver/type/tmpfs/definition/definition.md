# `tmpfs` Definition

- the `tmpfs` driver in Docker volumes is used to create temporary file storage in memory, rather than on disk;
- this can be useful for storing ephemeral data that doesn't need to persist after the container stops and should reside in RAM for fast access;
- the data:
  - **disappears when the container stops or is removed**;
  - is not written to disk, so it's fast and doesn't use disk I/O;
  - is limited by available system memory and optional size limits.
