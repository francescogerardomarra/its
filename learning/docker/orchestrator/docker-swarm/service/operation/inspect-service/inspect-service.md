# Inspect Services

- the following command shows detailed information about a service;
- the output is in JSON format:

    ```commandline
    docker service inspect myweb
    ```

    output:

    ```json
    [
        {
            "ID": "9x01yzul6njo5ufbbzo46x8pg",
            "Version": {
                "Index": 17
            },
            "CreatedAt": "2025-05-20T09:03:59.833291197Z",
            "UpdatedAt": "2025-05-20T09:03:59.833291197Z",
            "Spec": {
                "Name": "myweb",
                "Labels": {},
                "TaskTemplate": {
                    "ContainerSpec": {
                        "Image": "nginx:latest@sha256:c15da6c91de8d2f436196f3a768483ad32c258ed4e1beb3d367a27ed67253e66",
                        "Init": false,
                        "StopGracePeriod": 10000000000,
                        "DNSConfig": {},
                        "Isolation": "default"
                    },
                    "Resources": {
                        "Limits": {},
                        "Reservations": {}
                    },
                    "RestartPolicy": {
                        "Condition": "any",
                        "Delay": 5000000000,
                        "MaxAttempts": 0
                    },
                    "Placement": {
                        "Platforms": [
                            {
                                "Architecture": "amd64",
                                "OS": "linux"
                            },
                            {
                                "Architecture": "unknown",
                                "OS": "unknown"
                            },
                            {
                                "OS": "linux"
                            },
                            {
                                "Architecture": "unknown",
                                "OS": "unknown"
                            },
                            {
                                "OS": "linux"
                            },
                            {
                                "Architecture": "unknown",
                                "OS": "unknown"
                            },
                            {
                                "Architecture": "arm64",
                                "OS": "linux"
                            },
                            {
                                "Architecture": "unknown",
                                "OS": "unknown"
                            },
                            {
                                "Architecture": "386",
                                "OS": "linux"
                            },
                            {
                                "Architecture": "unknown",
                                "OS": "unknown"
                            },
                            {
                                "Architecture": "mips64le",
                                "OS": "linux"
                            },
                            {
                                "Architecture": "unknown",
                                "OS": "unknown"
                            },
                            {
                                "Architecture": "ppc64le",
                                "OS": "linux"
                            },
                            {
                                "Architecture": "unknown",
                                "OS": "unknown"
                            },
                            {
                                "Architecture": "s390x",
                                "OS": "linux"
                            },
                            {
                                "Architecture": "unknown",
                                "OS": "unknown"
                            }
                        ]
                    },
                    "ForceUpdate": 0,
                    "Runtime": "container"
                },
                "Mode": {
                    "Replicated": {
                        "Replicas": 3
                    }
                },
    
    ... and more ...
    ```
