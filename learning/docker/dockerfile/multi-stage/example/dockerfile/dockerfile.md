# `Dockerfile`

- in the following `Dockerfile` example we used three stages, `build`, `test` and `runtime`;
- each stage will be explained within the follow overview:
  - [`build` stage](../overview/build-stage/build_stage.md);
  - [`test` stage](../overview/test-stage/test_stage.md);
  - [`runtime` stage](../overview/runtime-stage/runtime_stage.md).

**`Dockerfile` example:**

```dockerfile
# --- Stage 1: Build ---
FROM maven:3.8.6-openjdk-8 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ src/
RUN mvn clean package -DskipTests

# --- Stage 2: Test ---
FROM build AS test
RUN mvn test

# --- Stage 3: Runtime ---
FROM openjdk:8-jre AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
```
