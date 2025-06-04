# Create and Connect a Container to Network

1. create a custom network `my-custom-network`:

    ```commandline
    docker network create my-custom-network
    ```

2. create a container `my-nginx-container` and connect it to `my-custom-network`:

    ```commandline
    docker run -d --name my-nginx-container --network my-custom-network nginx
    ```
