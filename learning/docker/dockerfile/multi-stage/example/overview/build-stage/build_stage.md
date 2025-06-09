# `build` Stage

In this chapter, we explain the `build` stage from the previous [Dockerfile](../../dockerfile/dockerfile.md); the stage:

1. copies the `pom.xml` within the image;
2. installs the dependencies:
    - `RUN mvn dependency:go-offline` downloads all dependencies;
    - without this command, `mvn clean package` would download the dependencies since they are not present;
    - when we build the image, if we didn't change the `pom.xml` from the previous build, we don't want to download dependencies again;
    - we want to reuse the previous Docker cached layers with all the dependencies present;
    - for this reason we:
      1. just copied the `pom.xml`;
      2. download the dependencies;
      3. just after copied the source code.
    - this avoids the cache invalidation for the dependency layer in case the source code changes;
    - you can see this problem within [layers](../../../../../layer/index.md) section.
3. generates the `.jar` file.

**`Dockerfile` `build` stage section:**

```dockerfile
# --- Stage 1: Build ---
FROM maven:3.8.6-openjdk-8 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ src/
RUN mvn clean package -DskipTests
```
