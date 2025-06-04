# Kubernetes Context

- a Kubernetes context is a configuration that allows `kubectl` to communicate with a specific cluster;
- it includes the cluster name, user credentials, and the default namespace;
- you can switch between multiple Kubernetes contexts to manage different clusters easily;
- namespaces in Kubernetes are logical partitions within a cluster that help organize and isolate resources;
- each Kubernetes context can have a default namespace, which determines where commands run when no namespace is specified;
- you can list all namespaces in a cluster using `kubectl get namespaces`;
- to work in a specific namespace temporarily, you can add the `--namespace=<namespace>` flag to `kubectl` commands;
- to set a default namespace for a context, you can use `kubectl config set-context <context-name> --namespace=<namespace>`.
