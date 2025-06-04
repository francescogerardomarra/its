# `test` Stage

In this chapter, we explain the `test` stage from the previous [Dockerfile](../../dockerfile/dockerfile.md); the stage:

1. uses the image generated at the previous stage as base image for `test` stage;
2. runs the tests;
3. if one of the stages within the `Dockerfile` fails, all the subsequent stages will not be executed and the build will fail.

**`Dockerfile` `test` stage section:**

```dockerfile
# --- Stage 2: Test ---
FROM build AS test
RUN mvn test
```
