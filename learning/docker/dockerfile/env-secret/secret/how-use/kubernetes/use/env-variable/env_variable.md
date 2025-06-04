# Use Secret as Env Variable

- see the [definition](../definition/definition.md) before reading this section;
- the secret values are injected as **environment variables** inside the container, making them easily accessible to the application;
- using secrets as environment variables in Kubernetes is not ideal because they can be exposed through process listings, logs, and are not automatically refreshed when updated; a more secure approach is to mount them as files inside the container.

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
      env:
        - name: USERNAME
          valueFrom:
            secretKeyRef:
              name: my-secret
              key: username
        - name: PASSWORD
          valueFrom:
            secretKeyRef:
              name: my-secret
              key: password
```
