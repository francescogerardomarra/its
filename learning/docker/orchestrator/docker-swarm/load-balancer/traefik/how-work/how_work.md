# How It Works With Swarm

- **Traefik monitors Swarm services** using Docker's API;
- it automatically configures itself to route external traffic to the right containers (services) based on labels you set on your services;
- it handles **routing**, **SSL (Let's Encrypt)**, and **load balancing** without manual config updates;


- it overrides the default routing mesh of Docker Swarm.
