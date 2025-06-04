# Apply the Deployment

Enter the root [project](../maven-project/download/download.md) folder with a terminal and run this command:

```commandline
kubectl apply -f deployment.yaml
```

**Above command explanation:**

- `kubectl apply -f deployment.yaml` is a command used in Kubernetes to apply or update resources defined in a YAML configuration file;
- `kubectl` is the Kubernetes command-line tool that allows users to interact with the Kubernetes cluster;
- `apply` is a command that applies a configuration to a resource by creating or updating it as defined in the YAML file;


- `-f deployment.yaml` specifies the file that contains the resource definitions to be applied to the cluster;
- the command ensures that the desired state described in `deployment.yaml` is maintained in the Kubernetes cluster;
- **the secret will be saved non-encrypted within the pod**.
