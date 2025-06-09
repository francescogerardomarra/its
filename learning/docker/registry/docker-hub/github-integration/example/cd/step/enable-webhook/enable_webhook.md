# Enable Docker Hub Webhook

Docker Hub lets you add a webhook to notify a URL when a new image is pushed:

1. go to your Docker Hub repository;
2. go to **Settings > Webhooks**;
3. add a new webhook with a URL like:

    ```http
    http://<your-server-ip>:8080/webhook
    ```

    this means your server must run a listener at port `8080`.
