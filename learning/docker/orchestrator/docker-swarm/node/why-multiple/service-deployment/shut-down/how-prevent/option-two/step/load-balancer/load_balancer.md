# Load Balancer

- you can integrate an external reverse proxy/load balancer to Swarm:
  - **Traefik** (the easiest to integrate with Docker Swarm);
  - NGINX;
  - HAProxy.
- they can detect containers marked as unhealthy and stop routing traffic to them;
- this resolves the [previous](../health-check/health_check.md) issue where traffic was still being sent to containers that had already received a `SIGTERM` signal from Docker and were in the process of shutting down;


- considering Traefik, it replaces the default Swarm load balancer for external (ingress) HTTP/HTTPS traffic;
- Traefik only handles ingress (incoming) traffic, usually on HTTP or HTTPS;
- if your services talk to each other internally using Swarm service names (e.g., api:80), then Swarm's internal load balancer is still used for that.
