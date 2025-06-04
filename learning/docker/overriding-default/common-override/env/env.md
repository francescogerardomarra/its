# Environment Variables

If the image uses environment variables, you can override them (e.g., `MY_VAR` is defined using `ENV` in the `Dockerfile`):

```commandline
docker run -e MY_VAR=value my-image
```
