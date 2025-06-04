# Check Kubernetes Context

- see what is a Kubernetes context [here](../../../../../../../orchestrator/kubernetes/context/context.md);
- minikube creates its own Kubernetes context, named `minikube`, which is separate from other Kubernetes contexts on your machine;
- other contexts may include connections to cloud providers like AWS (`eks`), Google Cloud (`gke`), Azure (`aks`), or self-hosted Kubernetes clusters;


- switching to the Minikube context does not delete or modify other contexts, only changes the active one;
- you can list all available contexts using `kubectl config get-contexts`;
- you can switch back to a different context at any time using `kubectl config use-context <context-name>`;


- Minikube does not interfere with cloud or production clusters unless explicitly configured to do so;
- when you switch to a non-production context, commands will not affect the production environment;
- this helps prevent accidental changes, deletions, or disruptions in a live production cluster.

**Check your current context:**

- command:

    ```commandline
    kubectl config current-context
    ```
- output:

    ```commandline
    minikube
    ```

**List all available contexts on your machine:**

- command:

    ```commandline
    kubectl config get-contexts
    ```

- output:

    ```commandline
    CURRENT   NAME                                                                  CLUSTER                                                               AUTHINFO                                                              NAMESPACE                                               
              my-context-test                                                       eks-my-project-test                                                   my-project-test                                                             
    *         minikube                                                              minikube                                                              minikube                                                              default
    ```
