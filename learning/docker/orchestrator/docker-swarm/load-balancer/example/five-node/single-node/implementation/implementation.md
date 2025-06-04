# Implementation

- create `A records` (kind of records that maps a domain to an `IPv4` address) **for only one** of the 5 node public IPs under the purchased domain;
- for example:

  ```
  mypublicapi.com A 1.2.3.4
  ```
- this works thanks to the Swarm routing mesh, which forwards all requests received by the `1.2.3.4` node to the appropriate node where the service is running.

**Downsides:**

- **single point of failure**, if that node is down, your API is unreachable;
- you lose the redundancy and failover benefits of your Swarm cluster.
