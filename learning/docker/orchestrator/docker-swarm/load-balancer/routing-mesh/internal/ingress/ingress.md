# Ingress Network Setup

- Docker automatically creates an **[overlay](../../../../networking/overlay/definition/definition.md) network called `ingress`**;
- every node connects to the ingress network;
- the **routing mesh uses this network** to route external traffic to available service replicas.
