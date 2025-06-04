# Port Conflicts

Since all containers on `--network host` share the same network namespace as the host:

- if a container A binds to port `8080`;
- then container B can’t bind to that same port at the same time;
- so it's like multiple apps running directly on your host: they need to use different ports or else they’ll clash;


- a container using the `host` network **cannot** start if its service tries to bind to a port that is already in use on the host.
