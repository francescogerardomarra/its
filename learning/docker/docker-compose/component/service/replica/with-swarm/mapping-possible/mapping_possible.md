# Why Is Static Port Mapping Possible in Docker Swarm?

- in Docker Compose standalone, when you define a port mapping like ["5000:5000"](../../../example/compose-file/compose_file.md), it means the host port `5000` is statically mapped to the container port `5000`;
- this static mapping works fine if there is only one replica of the container, but if you scale the service with multiple replicas, it will fail because multiple containers cannot bind to the same host port;
- this limitation makes Docker Compose unsuitable for running multiple replicas with fixed host port mappings;


- in Docker Swarm, port mapping works differently due to the **Ingress Load Balancer** feature, which automatically load-balances traffic across multiple replicas;
- instead of mapping the host port to each container directly, Swarm maps the host port to the **service**, not to individual containers;
- the service, in turn, distributes incoming traffic to the containers in a round-robin fashion, making it possible to use `"5000:5000"` with multiple replicas without conflicts;


- the internal load balancing mechanism of Docker Swarm handles the distribution of traffic to the individual container replicas;
- as a result, it is possible to use the same port number with multiple replicas in Docker Swarm while maintaining high availability and scalability.
