# View the Logs of the Pod

- open a terminal and run the following command:

    ```commandline
    kubectl logs java-app-56bdc665bd-6jts2
    ```

- output:

    ```commandline
    [SecretManager] secret: SuperSecretValue1245
    [Main] secret: SuperSecretValue1245
    ```

**Above command explanation:**

- retrieves the logs of a specific pod in a Kubernetes cluster;
- if the pod has multiple containers, this command will return logs only from the default container unless a specific container is specified.
