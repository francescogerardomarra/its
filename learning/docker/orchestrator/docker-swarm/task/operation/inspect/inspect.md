# Inspect Tasks

1. list all the services:

    ```commandline
    docker service ls
    ```
    
    output:
    
    ```commandline
    ID             NAME              MODE         REPLICAS   IMAGE          PORTS
    wgvr42ewrjqu   my-service        replicated   1/1        nginx:latest   
    6bxomvzmaq7b   myservice-redis   replicated   1/1        redis:latest   
    9x01yzul6njo   myweb             replicated   3/3        nginx:latest  
    ```
   
2. list all the tasks of a service (`myweb` in our case):

    ```commandline
    docker service ps myweb
    ```

    output:
    
    ```commandline
    ID             NAME          IMAGE          NODE        DESIRED STATE   CURRENT STATE          ERROR                              PORTS
    lw44jk515eve   myweb.1       nginx:latest   avolpe-pc   Running         Running 3 hours ago                                       
    nw4rvwu2nslj    \_ myweb.1   nginx:latest   avolpe-pc   Shutdown        Failed 3 hours ago     "No such container: myweb.1.nw…"   
    ou9yaydxf6i9    \_ myweb.1   nginx:latest   avolpe-pc   Shutdown        Shutdown 3 hours ago                                      
    te7tr78vtzsj   myweb.2       nginx:latest   avolpe-pc   Running         Running 3 hours ago                                       
    zn2dfmhc6tcm    \_ myweb.2   nginx:latest   avolpe-pc   Shutdown        Failed 3 hours ago     "No such container: myweb.2.zn…"   
    j0ukynqspasx    \_ myweb.2   nginx:latest   avolpe-pc   Shutdown        Shutdown 3 hours ago                                      
    l88l888d4lxf   myweb.3       nginx:latest   avolpe-pc   Running         Running 3 hours ago                                       
    o49fwm8s7es3    \_ myweb.3   nginx:latest   avolpe-pc   Shutdown        Failed 3 hours ago     "No such container: myweb.3.o4…"   
    djby3vmnkkwa    \_ myweb.3   nginx:latest   avolpe-pc   Shutdown        Shutdown 3 hours ago    
    ```

3. inspect a specific task (`lw44jk515eve` in our case, it is in `RUNNING` state), output is in JSON format:

    ```commandline
    docker inspect lw44jk515eve
    ```
    
    output:
    
    ```json
    [
        {
            "ID": "lw44jk515eve1meznq7mngtxk",
            "Version": {
                "Index": 86
            },
            "CreatedAt": "2025-05-21T07:44:37.727912786Z",
            "UpdatedAt": "2025-05-21T07:44:43.375560228Z",
            "Labels": {},
            "Spec": {
                "ContainerSpec": {
                    "Image": "nginx:latest@sha256:c15da6c91de8d2f436196f3a768483ad32c258ed4e1beb3d367a27ed67253e66",
                    "Init": false,
                    "DNSConfig": {},
                    "Isolation": "default"
                },
                "Resources": {
                    "Limits": {},
                    "Reservations": {}
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
                "ForceUpdate": 0
            },
            "ServiceID": "9x01yzul6njo5ufbbzo46x8pg",
            "Slot": 1,
            "NodeID": "wbkfxctwut6tuvtiru0s2r5uu",
            "Status": {
                "Timestamp": "2025-05-21T07:44:43.288901365Z",
                "State": "running",
                "Message": "started",
                "ContainerStatus": {
                    "ContainerID": "cadce0d4b1ee6d1c9d09e3063ab731732393929937f26dec91e548ca750b59ee",
                    "PID": 5335,
                    "ExitCode": 0
                },
                "PortStatus": {}
            },
            "DesiredState": "running",
            "Volumes": null
        }
    ]
    ```

4. inspect a specific task (`nw4rvwu2nslj` in our case, it is in `FAILED` state), output is in JSON format:

    ```commandline
    docker inspect nw4rvwu2nslj
    ```

    output:
    
    ```json
    [
        {
            "ID": "nw4rvwu2nsljrsgck62fk013k",
            "Version": {
                "Index": 75
            },
            "CreatedAt": "2025-05-20T09:59:55.466261552Z",
            "UpdatedAt": "2025-05-21T07:44:37.92818741Z",
            "Labels": {},
            "Spec": {
                "ContainerSpec": {
                    "Image": "nginx:latest@sha256:c15da6c91de8d2f436196f3a768483ad32c258ed4e1beb3d367a27ed67253e66",
                    "Init": false,
                    "DNSConfig": {},
                    "Isolation": "default"
                },
                "Resources": {
                    "Limits": {},
                    "Reservations": {}
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
                "ForceUpdate": 0
            },
            "ServiceID": "9x01yzul6njo5ufbbzo46x8pg",
            "Slot": 1,
            "NodeID": "wbkfxctwut6tuvtiru0s2r5uu",
            "Status": {
                "Timestamp": "2025-05-21T07:44:37.626949738Z",
                "State": "failed",
                "Message": "started",
                "Err": "No such container: myweb.1.nw4rvwu2nsljrsgck62fk013k",
                "ContainerStatus": {
                    "ContainerID": "5eab3162776856730188bfdd8f5671f7bb8ae54bc9fb4ef629a953f2d445b355",
                    "PID": 81046,
                    "ExitCode": 0
                },
                "PortStatus": {}
            },
            "DesiredState": "shutdown",
            "Volumes": null
        }
    ]
    ```

5. inspect a specific task (`ou9yaydxf6i9` in our case, it is in `SHUTDOWN` state), output is in JSON format:

    ```commandline
    docker inspect ou9yaydxf6i9
    ```

    output:
    
    ```commandline
    [
        {
            "ID": "ou9yaydxf6i99zrpcm5vsn05o",
            "Version": {
                "Index": 71
            },
            "CreatedAt": "2025-05-20T09:03:59.852001562Z",
            "UpdatedAt": "2025-05-21T07:44:37.724155112Z",
            "Labels": {},
            "Spec": {
                "ContainerSpec": {
                    "Image": "nginx:latest@sha256:c15da6c91de8d2f436196f3a768483ad32c258ed4e1beb3d367a27ed67253e66",
                    "Init": false,
                    "DNSConfig": {},
                    "Isolation": "default"
                },
                "Resources": {
                    "Limits": {},
                    "Reservations": {}
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
                "ForceUpdate": 0
            },
            "ServiceID": "9x01yzul6njo5ufbbzo46x8pg",
            "Slot": 1,
            "NodeID": "wbkfxctwut6tuvtiru0s2r5uu",
            "Status": {
                "Timestamp": "2025-05-21T07:44:37.626984807Z",
                "State": "shutdown",
                "Message": "shutdown",
                "ContainerStatus": {
                    "ContainerID": "66216051a2e09b396acad8d33294c960ed3bb8aea337d264e6080e499cc16b22",
                    "PID": 0,
                    "ExitCode": 0
                },
                "PortStatus": {}
            },
            "DesiredState": "shutdown",
            "Volumes": null
        }
    ]
    ```
