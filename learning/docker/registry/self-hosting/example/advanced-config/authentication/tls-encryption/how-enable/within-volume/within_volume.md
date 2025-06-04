#

- it's a common practice to place the `config.yml` file within a volume;
- storing the `config.yml` of a Docker registry in a volume ensures that configurations persist across container restarts and updates;
- by using a volume, the registry retains its authentication settings, storage backend configurations, and other essential parameters;
- this approach prevents data loss and avoids the need to reconfigure the registry each time the container is recreated.

**Command to place `config.yml` file within a volume:**

```commandline
docker run -d \
  --name registry \
  -v /path/to/config.yml:/etc/docker/registry/config.yml \
  -p 5000:5000 \
  registry:2
```
