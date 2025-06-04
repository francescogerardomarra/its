# Example

- in this example, we create a service running nginx, using all the options explained in the previous chapters;
- run this command on a manager node:

    ```commandline
    docker service create \
      --name nginx_service \
      --replicas 3 \
      -p 8080:80 \
      --constraint 'node.role == worker' \
      nginx
    ```

  - `--name nginx_service`: the service is named `nginx_service`;
  - `--replicas 3`: run 3 instances of the nginx container;
  - `-p 8080:80`: map port `8080` of the host to port `80` of the container;
  - `--constraint 'node.role == worker'`: ensures the service only runs on worker nodes.
