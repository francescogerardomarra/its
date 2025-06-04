# View Secret

- **listing all secrets:** use `kubectl get secrets` to view a list of all secrets in the current Kubernetes namespace;
- **viewing a specific secret:** use `kubectl get secret my-secret -o yaml` to retrieve detailed information about a specific secret in YAML format;
- **data encoding:** Kubernetes stores secrets in base64 encoding, so values in the output will need decoding for readability;


- **access control:** viewing secrets requires appropriate permissions, typically granted through Kubernetes RBAC (Role-Based Access Control).
