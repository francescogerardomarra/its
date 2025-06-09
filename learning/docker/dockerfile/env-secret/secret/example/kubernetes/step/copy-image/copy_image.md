# Copy the Image within Minikube

Open a terminal and run this command:

```commandline
minikube image load secret-kubernetes:1.0
```

**Above command explanation:**

- when you build an image locally, it is stored in your local Docker daemon's image repository;
- Minikube runs its own Docker daemon inside a virtual machine or container runtime, separate from your local machine;
- deployments in Kubernetes reference images from registries or the container runtime where the cluster nodes pull from;


- since your locally built image is not in the Minikube daemon's repository, Kubernetes cannot find it when deploying;
- the `minikube image load secret-kubernetes:1.0` command transfers the image from your local machine to Minikube's internal image storage, making it accessible to the cluster deployment.
