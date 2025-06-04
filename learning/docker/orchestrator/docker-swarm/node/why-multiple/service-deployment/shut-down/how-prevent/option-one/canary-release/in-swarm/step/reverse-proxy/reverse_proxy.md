# Use a Reverse Proxy for Traffic Splitting

- use a reverse proxy like **Traefik**, HAProxy or NGINX to control traffic distribution between the two services;
- configure it to send a small percentage (e.g., 5%) of the traffic to the canary (`myapp-v2`) and the rest to the stable (`myapp-v1`);
- traffic routing decisions can be made using criteria such as:
  - random request sampling, where a percentage of incoming requests are randomly directed to the canary version;
  - HTTP headers or cookies, allowing you to route requests from specific users (e.g., internal testers or beta users);
  - client IP addresses or user-agent strings, enabling targeting by geographic region, device type, or client application.
