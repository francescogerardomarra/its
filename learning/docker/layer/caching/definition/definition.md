# Caching Layers Definition

- caching is performed during the rebuild of the same image;
- caching means Docker remembers previously built layers and reuses them if the instruction and its context haven't changed;
- this saves time and avoids redoing unnecessary steps.
