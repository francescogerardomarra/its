# Use Secret Definition

- in the following sections you'll see the manifest defines a pod named `my-pod` with a single container using the `nginx` image:
  - [use secret as env variable](../env-variable/env_variable.md);
  - [use secret as volume](../volume/volume.md).
- the container gets `USERNAME` and `PASSWORD` from a **Kubernetes secret** named [my-secret](../../create/from-cli/from_cli.md);
- sensitive data is stored in the secret instead of the YAML file, following best security practices;


- you'll see the `kind: Pod` as `kind` attribute for the `pod.yaml` file:

  - `kind: Pod`:
      - is the smallest deployable unit in Kubernetes;
      - it runs one or more containers but does not automatically restart if it fails.
  - `kind: Deployment`:
      - the difference between `kind: Pod` and `kind: Deployment` is that `Deployments` manage multiple pods, providing scalability and self-healing;
      - if a pod crashes, a `Deployment` will automatically restart it and support rolling updates.
