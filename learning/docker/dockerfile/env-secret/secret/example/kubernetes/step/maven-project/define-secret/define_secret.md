# Define Secret within `deployment.yaml` File

- it's **not** possible to define the secret within `deployment.yaml` file;
- you need to add a Secret section, which is a separate Kubernetes resource containing sensitive data encoded in base64;
- secret section is normally uploaded separately from `deployment.yaml`, using `kubectl apply -f secret.yaml`;


- in Kubernetes, unlike Docker Swarm with `docker-compose`, you **cannot** directly specify a local file path inside `secret.yaml`.

**Example of a `secret.yaml`:**

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: my-secret
type: Opaque
data:
  SECRET_VALUE: U3VwZXJTZWNyZXRWYWx1ZTEyNDU=   # "SuperSecretValue1245" in base64
```
