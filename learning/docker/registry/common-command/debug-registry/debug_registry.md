# Debug Registry

- **purpose**: 
  - the `docker logs` command lets you access the logs of your Docker registry container;
  - the docker logs command is specific to Docker containers running locally on your machine or a host you manage;
  - this is useful for monitoring or debugging issues related to the registry's operation, such as authentication errors, network problems, or storage issues;
  - this command is not specific for Docker registry, but it's used for all containers, command details [here](../../../container/common-command/general/display/static/static.md).
- **syntax**: the command format is:

    ```bash
    docker logs <registry-container-name>
    ```  
    - `<registry-container-name>` is the name or ID of the running Docker registry container, you can find it using `docker ps`.
- **example**: to view logs for a registry container named `registry-secure`, run:

    ```bash
    docker logs registry-secure
    ```  
    this will display logs that can help diagnose issues or verify the registry's activity.
