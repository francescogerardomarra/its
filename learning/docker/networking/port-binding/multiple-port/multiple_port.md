# Multiple Ports

- you can bind multiple ports on the host to a single container:

    ```commandline
    docker run -p 5000:5000 -p 8000:80 some-app
    ```

- the above command binds:
  - host `5000` to container `5000` (maybe an API);
  - host `8000` to container `80` (maybe a UI).
