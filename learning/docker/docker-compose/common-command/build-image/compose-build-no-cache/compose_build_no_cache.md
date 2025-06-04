# `docker compose build --no-cache`

- this command does the same of [docker compose build](../compose-build/compose_build.md) but ignores all cached image layers and rebuilds everything from scratch;
- because `--no-cache` forces a full rebuild, even if nothing has changed from previous build;
- it ignores all cached layers and re-executes every step in the Dockerfile;


- even cached layers that could normally be reused, like dependency installations based on an unchanged `pom.xml`, are ignored when using `--no-cache`.