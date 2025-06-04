# Example Definition

`docker-compose.yml` file:

```yaml
services:
  app:
    image: myapp:latest
    build:
      context: ./app/
    pull_policy: never  # forces Compose to skip pulling
```

**Above example explanation:**

- it will **not** attempt to pull `myapp:latest` from a registry, even if it’s missing locally;
- it will immediately build the image using the context (`.`);
- once built, the image is tagged as `myapp:latest`;


- then, Compose will use that image to start the `app` service;
