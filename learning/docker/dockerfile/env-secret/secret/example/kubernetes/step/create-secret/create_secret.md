# Create a Secret

- open a terminal and run this command:

    ```commandline
    kubectl create secret generic my-secret --from-literal=SECRET_VALUE="SuperSecretValue12345"
    ```

**The above command:**

- creates a Kubernetes secret named `my-secret` within the current namespace of the Minikube cluster;
- stores the secret in **etcd**, the underlying key-value store used by Kubernetes;
- **etcd** is a distributed key-value store that Kubernetes uses to persist cluster data, including secrets, configurations, and state information;


- in Minikube, **etcd** runs locally inside the Minikube virtual machine or container, meaning the secret is stored on your machine but within Minikube’s internal environment;
- the secret is **base64-encoded** but not encrypted by default, meaning it can be decoded easily;
- Minikube’s **etcd** does not enable encryption at rest by default, so the secret remains in plain text within etcd unless encryption is manually configured;


- secret data can be retrieved using `kubectl get secret my-secret -o yaml`, but the values will be base64-encoded;
- to enhance security, enabling Kubernetes secret encryption in Minikube or using external secret management tools is recommended.
