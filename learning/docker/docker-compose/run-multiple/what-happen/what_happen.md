# What Happens

In this chapter, we see what happens executing the commands listed within the [previous](../intro/intro.md) section:

1. `docker compose up -d` (first time): builds images from Dockerfile and starts containers;
2. you change the `Dockerfile` (e.g., add a new dependency);
3. you run `docker compose up -d` again: Docker will **not** rebuild the images by default.

It uses the **existing cached image**, assuming nothing has changed, because `docker compose up` command doesn't check if the Dockerfile has changed.
