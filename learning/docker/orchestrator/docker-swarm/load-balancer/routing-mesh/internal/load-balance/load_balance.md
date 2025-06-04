# Load Balancing Mechanism

- Docker uses **IPVS**, a layer 4 load balancer, to handle traffic routing for services;
- when a service is created, each of its **tasks (containers)** is **registered as an endpoint** in IPVS;
- by default, **IPVS uses a round-robin algorithm** to distribute incoming requests **evenly** across these available tasks;


- if a task is not running on the node that received the request, Docker routes the request to a remote node using the **VXLAN-based overlay network** and **NAT** (which rewrites the packet so it is delivered correctly and the response is routed back properly).
