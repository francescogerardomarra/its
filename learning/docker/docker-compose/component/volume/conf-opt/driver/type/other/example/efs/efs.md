# Amazon EFS

In this chapter, we show an example using **Amazon EFS**:

```yaml
version: '3.8'

services:
  my_app:
    image: nginx:latest
    container_name: my_nginx_container
    ports:
      - "8080:80"
    volumes:
      - my_efs_volume:/usr/share/nginx/html
    restart: always

volumes:
  my_efs_volume:
    driver: efs
    driver_opts:
      o: tls
      device: fs-12345678:/myfolder
```

**Above `docker-compose.yml` explanation:**

- `efs` stands for Amazon Elastic File System (EFS);
- the `o: tls` option enables Transport Layer Security (TLS) to encrypt data in transit between the container and the EFS file system;
- the `device` option specifies the EFS file system ID and the folder path to mount;
 

- in this example, `fs-12345678:/myfolder`, where `fs-12345678` is the EFS file system ID and `/myfolder` is the directory within the EFS to be used as the root of the volume.
