# DNS in Docker Networks

- Docker has a built-in DNS server that works only within **user-defined networks**;
- this DNS lets containers resolve each other’s names by their container name or service name;
- Docker automatically assigns hostnames to containers using their names;


- the Docker DNS server runs at a special internal IP (usually `127.0.0.11` inside containers);
- the `/etc/resolv.conf` file inside containers will point to this DNS server.

**Example:**

- let’s say you create a user-defined bridge network:

    ```commandline
    docker network create my-net
    ```

- then you run two containers on that network:

    ```commandline
    docker run -d --name web --network my-net nginx
    docker run -it --name app --network my-net alpine sh
    ```

- inside `app`, you can do:

    ```commandline
    ping web
    ```
  
    output:
    
    ```commandline
    PING web (172.21.0.2): 56 data bytes
    64 bytes from 172.21.0.2: seq=0 ttl=64 time=0.155 ms
    64 bytes from 172.21.0.2: seq=1 ttl=64 time=0.134 ms
    64 bytes from 172.21.0.2: seq=2 ttl=64 time=0.127 ms
    64 bytes from 172.21.0.2: seq=3 ttl=64 time=0.061 ms
    ```
