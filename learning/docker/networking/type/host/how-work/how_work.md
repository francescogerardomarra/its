# How It Works

- when you use the `--network host` option with `docker run` command, the container shares the host's network stack directly;
- the container does not get its own IP address;
- it uses the same IP, ports, and interfaces as the host machine;


- **network isolation between the host and container is essentially removed**.
