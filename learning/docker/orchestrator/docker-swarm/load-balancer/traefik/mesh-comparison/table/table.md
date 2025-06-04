# Table

In this chapter, we compare Docker Swarm default routing mesh for distributing traffic among Swarm nodes with Traefik.

**Table:**

| Feature                    | Routing mesh      | Traefik                                         |
|----------------------------|-------------------|-------------------------------------------------|
| Load balancing             | basic round-robin | advanced (round-robin, sticky, weighted, etc.)  |
| Aware of HTTP/HTTPS        | no                | yes                                             |
| Smart routing (paths etc.) | no                | yes                                             |
| TLS termination            | no                | yes (e.g. Let's Encrypt)                        |
| Middleware support         | no                | yes                                             |
| Port publishing needed     | yes               | no (uses internal networks)                     |
| Transparent to user        | yes               | yes (but requires some developer configuration) |
| Session stickiness         | no                | yes                                             |
| Cross-node traffic         | often             | avoided if running **global** Traefik           |

\* cross-node traffic happens when a request arrives at one node, but the container handling the request lives on another node.

