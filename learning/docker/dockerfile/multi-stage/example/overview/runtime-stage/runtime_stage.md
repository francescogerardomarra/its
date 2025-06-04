# `runtime` Stage

In this chapter, we explain the `runtime` stage from the previous [Dockerfile](../../dockerfile/dockerfile.md); the stage:

1. creates a light image, containing just the essential to run the `.jar` file;
2. copies the `.jar` file generated at the [`build` stage](../build-stage/build_stage.md);
3. sets the command to launch when a container will be instantiated from the final image.

**`Dockerfile` `runtime` stage section:**

```dockerfile
# --- Stage 3: Runtime ---
FROM openjdk:8-jre AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
```

