# Example

- say you’re running a server (maybe a VPS or cloud instance), and you do this:

    ```commandline
    docker run -p 8080:80 nginx
    ```

- the container listens on port `80` (NGINX default);
- you bind it to your host’s port `8080`.
- now, anyone visiting `http://<your-server-ip>:8080` can access the NGINX service inside the container.
