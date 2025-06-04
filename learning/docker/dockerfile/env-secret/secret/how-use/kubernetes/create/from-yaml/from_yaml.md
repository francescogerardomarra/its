# Create From `yaml` File

1. **define the secret in a YAML file:**
   - the manifest specifies `apiVersion: v1` and `kind: Secret`, indicating it's a Kubernetes secret.
   
2. **set metadata and type:**
   - the `metadata` section includes a unique `name` (`my-secret` in this case);
   - the `type: Opaque` means it's a generic secret, commonly used for storing credentials.

3. **encode data in Base64:**
   - sensitive values like `username` and `password` must be **Base64 encoded** before being stored in the YAML file;
   - for example, `"admin"` is encoded as `YWRtaW4=`, and `"Pa$$w0rd"` is `UGEkJHcwcmQ=`.

4. **apply the secret to the cluster:**
   - use the command:
     ```sh
     kubectl apply -f secret.yaml
     ```  
   - this creates the Secret in the Kubernetes cluster.

5. **access the secret in a pod**
  - secrets can be mounted as environment variables or volumes in a pod to securely provide credentials;
  - example: a pod can reference `my-secret` to retrieve `username` and `password` securely.

**secret.yaml file:**

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: my-secret
type: Opaque
data:
  username: YWRtaW4=   # Base64 encoded value of "admin"
  password: UGEkJHcwcmQ=  # Base64 encoded value of "Pa$$w0rd"
```
