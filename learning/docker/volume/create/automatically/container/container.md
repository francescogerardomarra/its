# Container Initialization

If you run a container with `-v volume_name:/path/in/container` and `volume_name` doesn't exist yet, Docker automatically creates it:

```commandline
docker run -d -v myvolume:/app/data nginx
```
