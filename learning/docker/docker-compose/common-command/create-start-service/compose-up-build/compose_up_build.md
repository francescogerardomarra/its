# `docker compose up --build`

- this command is the same of previous [docker compose up](../compose-up/compose_up.md) command, **but rebuild the images before starting the containers**;
- it’s useful when you’ve made changes to your `Dockerfile` or app code, and you want to make sure the containers are running with the latest version of your app;
- if you change your `Dockerfile` or app code, `docker compose up` without `--build` flag wouldn't rebuild the images if they are already present.
