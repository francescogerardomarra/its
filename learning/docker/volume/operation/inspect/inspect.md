# Inspect

- this command is used to retrieve detailed information about a specific Docker volume:

    ```commandline
    docker volume inspect my_volume
    ```

- output:

    ```commandline
    [
        {
            "CreatedAt": "2025-04-30T15:12:26+02:00",
            "Driver": "local",
            "Labels": null,
            "Mountpoint": "/var/lib/docker/volumes/my_volume/_data",
            "Name": "my_volume",
            "Options": null,
            "Scope": "local"
        }
    ]
    ```
