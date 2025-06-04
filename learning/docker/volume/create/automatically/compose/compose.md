# `docker-compose.yml` File

When you define a volume inside a `docker-compose.yml`, Docker creates it automatically when you run `docker-compose up`.

**Example** 

- `docker-compose.yml`:

    ```dockerfile
    version: '3'
    services:
      web:
        image: nginx
        volumes:
          - myvolume:/usr/share/nginx/html
    
    volumes:
      myvolume:
    ```

- when you run `docker-compose up -d`, `myvolume` gets created;
- if you don't define it explicitly under `volumes:`, Docker will create an anonymous volume:

    ```dockerfile
    version: '3'
    services:
      app:
        image: nginx
        volumes:
          - /usr/share/nginx/html
    ```

  - when you run `docker-compose up -d`, Docker will create a random, anonymous volume and mount it to `/usr/share/nginx/html` inside the container.
