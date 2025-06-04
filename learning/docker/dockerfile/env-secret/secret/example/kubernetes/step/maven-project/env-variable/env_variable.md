# Secret as Env Variable

- instead of mounting the secret within a volume, we could mount as container env variable;
- this way is **not** recommended:

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
    
            env:  # Define environment variables from a secret
              - name: MY_SECRET_ENV  # The environment variable name in the container
                valueFrom:
                  secretKeyRef:
                    name: my-secret  # Name of the Kubernetes Secret
                    key: SECRET_VALUE  # Key from the secret
    ```
