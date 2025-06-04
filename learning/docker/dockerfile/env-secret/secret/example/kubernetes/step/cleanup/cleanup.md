# Cleanup (Optional)

- **delete the deployment**:
  ```sh
  kubectl delete deployment java-app
  ```

    - when executed, this command removes the deployment and all the associated pods, but not the underlying persistent storage, ConfigMaps, or secrets that may be linked to it;
    - if you want to stop the pods without removing the deployment see [here](../../operation/stop/stop.md).    

- **delete the secret**:
  ```sh
  kubectl delete secret my-secret
  ```

  when executed, this command permanently removes the specified secret, which may contain sensitive data like passwords, API keys, or certificates.
