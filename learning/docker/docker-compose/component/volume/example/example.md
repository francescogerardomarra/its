# Example

**`docker-compose.yml` file:**

```yaml
version: '3.9'

services:
  app:
    image: nginx:alpine
    ports:
      - "8080:80"
    volumes:
      - shared-data:/usr/share/nginx/html
    depends_on:
      - db

volumes:
  shared-data:
```

**Volume explanation:**

- the volume named `shared-data` is defined under the `volumes` key at the bottom of the `docker-compose.yml` file;
- it is a named volume, which means Docker manages it independently of the container lifecycle;
- the volume is mounted in the `app` service at the path `/usr/share/nginx/html`;


- this path corresponds to the default directory where NGINX serves static files;
- any files stored in the volume will be accessible to the NGINX server inside the container;
- the volume persists data even if the container is stopped or removed;


- since the volume is defined without specifying a host path, Docker automatically creates and manages the volume's storage location;
- this setup ensures that static files and web content are preserved across container restarts.
