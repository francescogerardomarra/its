# `kubectl`

- `kubectl` is the Kubernetes CLI (Command-Line Interface);
- it allows you to interact with any Kubernetes cluster (AWS EKS, Minikube, Google Kubernetes Engine, etc.);
- it does not create Kubernetes clusters, it only connects to and manages them;


- you can install `kubectl` on your system without Minikube and use it to manage remote clusters like AWS EKS.

**Install `kubectl`:**

1. check if `kubectl` is already present:

    ```commandline
    kubectl version --client
    ```
2. otherwise, install it:

    ```commandline
    sudo apt update
    sudo apt install -y kubectl
    sudo snap install kubectl --classic
    kubectl version --client
    ```
