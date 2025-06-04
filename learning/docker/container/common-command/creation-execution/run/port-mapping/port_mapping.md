# Run With Port Mapping

- the following command runs a new container from an image, mapping a port on the host machine to a port inside the container;
- with this mapping, every request sent to the host's `host_port` will be redirected to the container's `container_port`.

    ```bash
    docker run -p <host_port>:<container_port> <image_name>
    ```

- executed command:

    ```bash
    docker run -p 8080:80 mongo
    ```

- output:

    ```bash
    Unable to find image 'mongo:latest' locally
    latest: Pulling from library/mongo
    afad30e59d72: Pull complete 
    2ab913c649fa: Pull complete 
    142bff30356f: Pull complete 
    ea6a78a8bfa5: Pull complete 
    e87de320d14a: Pull complete 
    e8fb995504bd: Pull complete 
    edbe36f78898: Pull complete 
    1f774f57f04f: Pull complete 
    Digest: sha256:c165af1a407eefce644877bf5a59ba3d9ca762e62b4f1723c919dc08dc32f4d0
    Status: Downloaded newer image for mongo:latest
    {"t":{"$date":"2024-11-28T14:07:35.533+00:00"},"s":"I",  "c":"CONTROL",  "id":23285,   "ctx":"main","msg":"Automatically disabling TLS 1.0, to force-enable TLS 1.0 specify --sslDisabledProtocols 'none'"}
    {"t":{"$date":"2024-11-28T14:07:35.533+00:00"},"s":"I",  "c":"CONTROL",  "id":5945603, "ctx":"main","msg":"Multi threading initialized"}
    {"t":{"$date":"2024-11-28T14:07:35.534+00:00"},"s":"I",  "c":"NETWORK",  "id":4648601, "ctx":"main","msg":"Implicit TCP FastOpen unavailable. If TCP FastOpen is required, set at least one of the related parameters","attr":{"relatedParameters":["tcpFastOpenServer","tcpFastOpenClient","tcpFastOpenQueueSize"]}}
    {"t":{"$date":"2024-11-28T14:07:35.535+00:00"},"s":"I",  "c":"NETWORK",  "id":4915701, "ctx":"main","msg":"Initialized wire specification","attr":{"spec":{"incomingExternalClient":{"minWireVersion":0,"maxWireVersion":25},"incomingInternalClient":{"minWireVersion":0,"maxWireVersion":25},"outgoing":{"minWireVersion":6,"maxWireVersion":25},"isInternalClient":true}}}
    {"t":{"$date":"2024-11-28T14:07:35.535+00:00"},"s":"I",  "c":"TENANT_M", "id":7091600, "ctx":"main","msg":"Starting TenantMigrationAccessBlockerRegistry"}
    {"t":{"$date":"2024-11-28T14:07:35.535+00:00"},"s":"I",  "c":"CONTROL",  "id":4615611, "ctx":"initandlisten","msg":"MongoDB starting","attr":{"pid":1,"port":27017,"dbPath":"/data/db","architecture":"64-bit","host":"d7efb948f2ab"}}
    ```

- list running containers:

    ```bash
    CONTAINER ID   IMAGE        COMMAND                  CREATED         STATUS         PORTS                                NAMES
    d7efb948f2ab   mongo        "docker-entrypoint.s…"   7 seconds ago   Up 6 seconds   27017/tcp, 0.0.0.0:8080->80/tcp      romantic_brown
    342998e4eec5   redis        "docker-entrypoint.s…"   3 hours ago     Up 3 hours     6379/tcp                             ecstatic_leakey
    f6f175294090   nginx        "/docker-entrypoint.…"   3 hours ago     Up 3 hours     80/tcp                               intelligent_lewin
    46fbfb5d5e8d   httpd        "httpd-foreground"       4 hours ago     Up 4 hours     80/tcp                               dreamy_matsumoto
    45f6798e8c9b   httpd        "httpd-foreground"       4 hours ago     Up 4 hours     80/tcp                               pedantic_payne
    3dc0044aed4b   registry:2   "/entrypoint.sh /etc…"   16 months ago   Up 5 hours     5000/tcp, 0.0.0.0:32000->32000/tcp   registry
    ```

- note: 
  - in the `PORTS` column, you see the port exposed within the Dockerfile with `EXPOSE` command;
  - the `EXPOSE` ports are not really exposed, it is just for documentation (`27017/tcp`, `6379/tcp`, `80/tcp`, `5000/tcp`);
  - the ports represented with `->` are really exposed to the host (`0.0.0.0:8080->80/tcp`, `0.0.0.0:32000->32000/tcp`);
  - these ports are exposed during container creation with `-p` flag.
