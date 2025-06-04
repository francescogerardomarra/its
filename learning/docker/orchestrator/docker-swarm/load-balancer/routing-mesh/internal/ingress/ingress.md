# Ingress Network Setup

- Docker automatically creates an **overlay network called `ingress`**; <!-- todo: link to overlay network -->
- every node connects to the ingress network;
- the **routing mesh uses this network** to route external traffic to available service replicas.
