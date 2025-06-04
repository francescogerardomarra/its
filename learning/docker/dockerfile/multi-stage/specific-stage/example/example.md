# Example

In this chapter, we show an example of how to build a specific stage within a multi-stage `Dockerfile`.

**Dockerfile:**

```dockerfile
# Stage 1: Build
FROM node:18 AS builder
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Runtime
FROM node:18 AS runtime
WORKDIR /app
COPY --from=builder /app/dist ./dist
CMD ["node", "dist/index.js"]
```

**Building Only the `builder` Stage:**

- this command stops at the `builder` stage and produces an intermediate image:

    ```commandline
    docker build --target builder -t my-builder-image .
    ```

- `--target` can execute just one stage at a time;
- one reason to run just the `builder` stage is to push that image to a registry for reuse;
- since it's an intermediate image, it would normally be discarded after the full multi-stage build.
