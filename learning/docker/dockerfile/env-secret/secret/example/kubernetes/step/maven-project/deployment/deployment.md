# `deployment.yaml` Explanation

Explanation with comments:

```yaml
apiVersion: apps/v1  # Specifies the API version being used
kind: Deployment  # Defines the type of Kubernetes resource (Deployment)
metadata:
  name: java-app  # Name of the deployment

spec:
  replicas: 1  # Specifies the number of pod replicas to run
  selector:
    matchLabels:
      app: java-app  # Selects pods with this label

  template:  # Defines the pod template
    metadata:
      labels:
        app: java-app  # Labels the pod with "app: java-app"

    spec:
      containers:
      - name: java-app  # Name of the container
        image: secret-kubernetes:1.0  # Docker image to use for the container

        volumeMounts:  # Specifies volume mounts for the container
          - name: secret-volume  # References the volume defined below
            mountPath: "/etc/secrets"  # Mounts the secret at this path inside the container
            readOnly: true  # Ensures the secret is read-only

      volumes:  # Defines the volumes used by the pod
        - name: secret-volume  # Volume name
          secret:
            secretName: my-secret  # Uses the Kubernetes Secret named "my-secret"
```
