# Example

Inside this container, trying something like `ping google.com` would fail because it has no network access:

```commandline
docker run --network none ubuntu
```
