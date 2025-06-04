# Anonymous Volumes

Since anonymous volumes don't have a name, you can't reference them easily across containers:

1. start your container:

    ```commandline
    docker run -d --name container-1 -v /app/data nginx
    ```
2. inspect the container to find the anonymous volume name assigned by docker:

    ```commandline
    docker inspect container-1
    ```

    output (under the `Mounts` section):

    ```commandline
    "Mounts": [
                {
                    "Type": "volume",
                    "Name": "ae4d058d91213f64b526953ebe8ac04796b43f78edbebb834d2446aeda9f6724",
                    "Source": "/var/lib/docker/volumes/ae4d058d91213f64b526953ebe8ac04796b43f78edbebb834d2446aeda9f6724/_data",
                    "Destination": "/app/data",
                    "Driver": "local",
                    "Mode": "",
                    "RW": true,
                    "Propagation": ""
                }
            ],
    ```

3. you can then share it like a [named volume](../named/named.md), since it's just a regular volume with an automatically generated name:

    ```commandline
    docker run -d --name container-2 -v ae4d058d91213f64b526953ebe8ac04796b43f78edbebb834d2446aeda9f6724:/tmp/my-folder nginx
    ```

4. check that `container-2` has the correct volume mounted:

    ```commandline
    docker inspect container-2
    ```

    output:

    ```commandline
    "Mounts": [
        {
            "Type": "volume",
            "Name": "ae4d058d91213f64b526953ebe8ac04796b43f78edbebb834d2446aeda9f6724",
            "Source": "/var/lib/docker/volumes/ae4d058d91213f64b526953ebe8ac04796b43f78edbebb834d2446aeda9f6724/_data",
            "Destination": "/tmp/my-folder",
            "Driver": "local",
            "Mode": "z",
            "RW": true,
            "Propagation": ""
        }
    ],
    ```
