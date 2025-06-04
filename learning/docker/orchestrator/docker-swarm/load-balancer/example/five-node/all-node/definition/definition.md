# Associate All Nodes With the Domain Definition

**Question:**

>- I have a Docker Swarm backend cluster with 5 nodes, and I'm running a backend service with 20 replicas distributed across these nodes;
>- I've purchased the domain **mypublicapi.com** to expose this service;
>- in this scenario, what is the best practice for DNS configuration?
>- should I map **all 5 node IP addresses** to **mypublicapi.com**, or is it sufficient to map **just one** of the node IPs to the domain?

**Answer:**

Map all 5 node IPs to `mypublicapi.com` (recommended for high availability).
