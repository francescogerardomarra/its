# Inspect a Container Details

- the following command returns a JSON-formatted output with metadata about the container:

   ```bash
   docker inspect <container_name|container_id>
   ```

- executed command:

    ```bash
    docker inspect 46fbfb5d5e8d
    ```

- output:

    ```bash
    [
        {
            "Id": "46fbfb5d5e8de4154992a9ebbe87f36a03ab00444a0cb6bb8223ac30648389f0",
            "Created": "2024-11-28T09:37:56.05053986Z",
            "Path": "httpd-foreground",
            "Args": [],
            "State": {
                "Status": "running",
                "Running": true,
                "Paused": false,
                "Restarting": false,
                "OOMKilled": false,
                "Dead": false,
                "Pid": 12058,
                "ExitCode": 0,
                "Error": "",
                "StartedAt": "2024-11-28T09:44:17.732330809Z",
                "FinishedAt": "2024-11-28T09:38:02.908137479Z"
            },
            "Image": "sha256:dad6ca1caf78e98e22e8e5a406ab4a3427cedfff254c7fcc9dc3b2ca922c642b",
            "ResolvConfPath": "/var/lib/docker/containers/46fbfb5d5e8de4154992a9ebbe87f36a03ab00444a0cb6bb8223ac30648389f0/resolv.conf",
            "HostnamePath": "/var/lib/docker/containers/46fbfb5d5e8de4154992a9ebbe87f36a03ab00444a0cb6bb8223ac30648389f0/hostname",
            "HostsPath": "/var/lib/docker/containers/46fbfb5d5e8de4154992a9ebbe87f36a03ab00444a0cb6bb8223ac30648389f0/hosts",
            "LogPath": "/var/lib/docker/containers/46fbfb5d5e8de4154992a9ebbe87f36a03ab00444a0cb6bb8223ac30648389f0/46fbfb5d5e8de4154992a9ebbe87f36a03ab00444a0cb6bb8223ac30648389f0-json.log",
            "Name": "/dreamy_matsumoto",
            "RestartCount": 0,
            "Driver": "overlay2",
            "Platform": "linux",
            "MountLabel": "",
            "ProcessLabel": "",
            "AppArmorProfile": "docker-default",
            "ExecIDs": null,
            "HostConfig": {
                "Binds": null,
                "ContainerIDFile": "",
                "LogConfig": {
                    "Type": "json-file",
                    "Config": {}
                },
                "NetworkMode": "bridge",
                "PortBindings": {},
                "RestartPolicy": {
                    "Name": "no",
                    "MaximumRetryCount": 0
                },
                
        ... and more ...
        
    ]
    ```
