# IPVS and Load Balancing

Docker configures **IPVS (IP Virtual Server)**, a **Layer 4 (transport layer) load balancer**, directly in the Linux kernel to distribute incoming traffic based on TCP and UDP ports:

- when a request hits any node on the published port, IPVS checks the service's VIP;
- IPVS then forwards the request to one of the **available tasks (containers) in the service**, either locally or on a remote node.
