# How `latest` Is Assigned?

- `latest` tag for an image, is normally changed, by a Docker image admin (or maintainer) when a new version of the image is deemed stable or production-ready, and they want to designate it as the default version users should pull when no specific tag is specified;
- the process involves removing the `latest` tag from the previous image and assigning it to the new one;
- in the next [example](../example/example.md) we:
  - create an image with tag `myapp:1.0`;
  - assign also the tag `latest` to it;
  - after, create a new version of the image and tag it `myapp:2.0`;
  - move the `latest` tag from `myapp:1.0` to `myapp:2.0`.
