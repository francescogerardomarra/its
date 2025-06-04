# Implementation

- create `A records` (kind of records that maps a domain to an `IPv4` address) for each of the 5 node public IPs under the same domain;
- for example:

  ```
  mypublicapi.com A 1.2.3.4
  mypublicapi.com A 5.6.7.8
  mypublicapi.com A 9.10.11.12
  mypublicapi.com A 13.14.15.16
  mypublicapi.com A 17.18.19.20
  ```

**Benefits:**

  - load is distributed at the DNS level (**round-robin** by most DNS resolvers);
  - if one node becomes unavailable, clients can still reach the service through the remaining IPs;
  - as long as they retry failed requests and a separate mechanism (like a load balancer or service mesh) is used to route just around unhealthy nodes;


  - Docker Swarm can route traffic internally to any healthy replica across the cluster, regardless of which node received the request.

**Caveats:**

- DNS round-robin isn't true load balancing (**no health checks**), but it offers basic fault tolerance;
- you should still use an external load balancer or reverse proxy like **Traefik**, **HAProxy**, or **NGINX** deployed across nodes to smartly distribute traffic and handle SSL termination, routing, etc.
