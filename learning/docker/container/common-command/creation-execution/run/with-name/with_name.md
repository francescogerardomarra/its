# Run with a Name

- the following command runs a new container allowing you to specify a custom name for it;
- `<container_name>` is the name you want to assign to the container;
- this makes it easier to identify and manage the container instead of relying on the randomly generated name Docker would otherwise assign.

    ```bash
    docker run --name <container_name> <image_name>
    ```

- executed command:

    ```bash
    docker run --name my-container caddy
    ```

- output:

    ```bash
    {"level":"info","ts":1732803941.0935159,"msg":"using config from file","file":"/etc/caddy/Caddyfile"}
    {"level":"info","ts":1732803941.093971,"msg":"adapted config to JSON","adapter":"caddyfile"}
    {"level":"info","ts":1732803941.09446,"logger":"admin","msg":"admin endpoint started","address":"localhost:2019","enforce_origin":false,"origins":["//localhost:2019","//[::1]:2019","//127.0.0.1:2019"]}
    {"level":"warn","ts":1732803941.09454,"logger":"http.auto_https","msg":"server is listening only on the HTTP port, so no automatic HTTPS will be applied to this server","server_name":"srv0","http_port":80}
    {"level":"info","ts":1732803941.0946262,"logger":"tls.cache.maintenance","msg":"started background certificate maintenance","cache":"0xc0005a2280"}
    {"level":"info","ts":1732803941.094648,"logger":"http.log","msg":"server running","name":"srv0","protocols":["h1","h2","h3"]}
    {"level":"info","ts":1732803941.0948348,"msg":"autosaved config (load with --resume flag)","file":"/config/caddy/autosave.json"}
    {"level":"info","ts":1732803941.0948396,"msg":"serving initial configuration"}
    {"level":"info","ts":1732803941.095672,"logger":"tls","msg":"cleaning storage unit","storage":"FileStorage:/data/caddy"}
    {"level":"info","ts":1732803941.095787,"logger":"tls","msg":"finished cleaning storage units"}
    ```

- list running containers:

    ```bash
    CONTAINER ID   IMAGE        COMMAND                  CREATED          STATUS         PORTS                                NAMES
    6fb17474df82   caddy        "caddy run --config …"   10 seconds ago   Up 9 seconds   80/tcp, 443/tcp, 2019/tcp, 443/udp   my-container
    342998e4eec5   redis        "docker-entrypoint.s…"   3 hours ago      Up 3 hours     6379/tcp                             ecstatic_leakey
    f6f175294090   nginx        "/docker-entrypoint.…"   3 hours ago      Up 3 hours     80/tcp                               intelligent_lewin
    46fbfb5d5e8d   httpd        "httpd-foreground"       5 hours ago      Up 5 hours     80/tcp                               dreamy_matsumoto
    45f6798e8c9b   httpd        "httpd-foreground"       5 hours ago      Up 5 hours     80/tcp                               pedantic_payne
    3dc0044aed4b   registry:2   "/entrypoint.sh /etc…"   16 months ago    Up 5 hours     5000/tcp, 0.0.0.0:32000->32000/tcp   registry
    ```
