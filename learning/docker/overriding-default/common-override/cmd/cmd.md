# Container Default Command (`CMD`)

- in a `docker run` command, anything you put after the image name is used to override the `CMD` defined in the `Dockerfile`;
- the image may define a default `CMD`, like:

    ```dockerfile
    CMD ["python", "app.py"]
    ```

- but when you run the container, you can override that like this:

    ```dockerfile
    docker run my-image echo "Hello"
    ```


- now it won’t run `python app.py`, it’ll just echo "Hello".
