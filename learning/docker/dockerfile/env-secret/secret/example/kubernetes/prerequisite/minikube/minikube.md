# Minikube

- Minikube is a tool that creates a small, local Kubernetes cluster on your machine;
- it runs a single-node Kubernetes cluster for testing and learning;
- Minikube provides a local Kubernetes environment, but it does **not** replace [kubectl](../kubectl/kubectl.md);
 

- Minikube requires `kubectl` to interact with the local Kubernetes cluster it creates.

**Install Minikube:**

1. check if Minikube is already present:

    ```commandline
    minikube version
    ```

2. otherwise, install it:

    ```commandline
    curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
    sudo install minikube-linux-amd64 /usr/local/bin/minikube
    minikube version
    ```
