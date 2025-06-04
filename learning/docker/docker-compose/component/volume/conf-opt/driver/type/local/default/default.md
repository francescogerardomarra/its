# Default

- docker Compose uses the default local driver when no driver is specified explicitly in the configuration file;
- the default local driver is named `local` and is used to store data directly on the host filesystem;
- volumes created with the local driver are managed as directories within Docker’s storage area, typically located under `/var/lib/docker/volumes/`;
 

- the local driver ensures that volume data is persistent even if containers are removed or restarted;
- it is suitable for storing data that should remain on the host machine and is not intended for sharing between hosts.

**Example:**

```yaml
version: '3.9'
services:
  web:
    image: nginx:latest
    ports:
      - "8080:80"
    volumes:
      - my_local_volume:/usr/share/nginx/html

volumes:
  my_local_volume:
    driver: local # Not necessary since "local" it's the default driver
```
