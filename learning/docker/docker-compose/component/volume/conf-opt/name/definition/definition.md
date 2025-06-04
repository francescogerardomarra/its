# Name

- by default a volume name is `<project_name>_<volume_name>`;
- with this option, you can override it;
- the `name` option in Docker Compose is used to specify a custom name for a volume;


- specifying a `name` allows you to control the actual name of the volume on the host system, overriding the default behavior;


- the `name` allows you to distinguish between different volumes more easily, especially in complex environments with multiple volumes;


- the volume name defined under `name` will be shown when you run `docker volume ls` or `docker volume inspect <volume_name>`.

**Example:**

```yaml
version: '3'
services:
  app:
    image: my-app
    volumes:
      - mydata:/data

volumes:
  mydata:
    name: custom-data-volume
```
