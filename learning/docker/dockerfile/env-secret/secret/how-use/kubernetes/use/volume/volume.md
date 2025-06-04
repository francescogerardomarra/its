# Use Secret as Volume

- see the [definition](../definition/definition.md) before reading this section;
- the Secret values are mounted as **files inside the container** at `/etc/secret`, making them securely accessible to the application;
- using secrets as mounted files in Kubernetes is a better practice than [environment variables](../env-variable/env_variable.md), as it reduces exposure through process listings or logs and allows automatic updates when the Secret changes;


- `readOnly: true`:
  - ensures that the container cannot modify the mounted secret files; 
  - this prevents accidental or malicious changes to the secret data, maintaining integrity and security.
- making the volume read-only enhances security by restricting write access;
- since secrets should only be read by the application, this prevents unauthorized modifications that could compromise sensitive credentials.

**`pod.yaml` file:**

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: my-pod
spec:
  containers:
    - name: my-container
      image: nginx
      volumeMounts:
        - name: secret-volume
          mountPath: "/etc/secret"
          readOnly: true
  volumes:
    - name: secret-volume
      secret:
        secretName: my-secret
```
