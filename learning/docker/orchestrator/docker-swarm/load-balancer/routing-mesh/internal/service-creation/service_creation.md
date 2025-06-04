# Service Creation and Publishing

When you create a Docker Swarm service with a **published port**, Docker:

- allocates a **virtual IP (VIP)** for the service;
- sets up IPVS rules using `iptables` and/or `ipvsadm`;
- ensures the published port is **listened to on every node** (via the `ingress` network).

**Example:**

```bash
docker service create --name web --publish published=8080,target=80 nginx
```
