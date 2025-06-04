# `docker run` with `-e` Flag

- the `-e` flag in `docker run` is used to pass environment variables to the container, allowing you to define variables inside the container at runtime;
- in this example, `APP_ENV=production` sets the `APP_ENV` variable inside the container, which can be accessed by processes running within it.

```bash
docker run -e APP_ENV=production my_image
```
