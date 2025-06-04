# Comparison with Routing Mesh Definition

**Default routing mesh:**

- when you **publish a port** (`--publish 80:80`), Docker makes it accessible on **all nodes**;
- incoming traffic to any node is **intercepted by Docker**, then **internally routed** (via VXLAN) to any service instance, **regardless of where it runs**;
- the **load balancing** is **round-robin** at the connection level, not HTTP-aware;
 

- no traffic awareness: doesn’t know about request paths, headers, session stickiness, etc.;
- **key limitation**: you have **no control** over the routing behavior, it’s basic and opaque.

**Traefik:**

- Traefik **reads service metadata** (via Docker API or labels);
- it knows **where each container is** and uses **internal Docker networks** (not `--publish`) to route traffic;
- **Traffic is distributed** to service instances **based on rules you define**, including:
  - load balancing (round-robin, sticky sessions, etc.);
  - request path (`/api`, `/admin`);
  - headers, cookies;
  - custom middlewares (rate limit, redirect, auth).


- it routes traffic **on Layer 7 (HTTP/HTTPS)**, unlike Swarm mesh which is Layer 4 (TCP).
