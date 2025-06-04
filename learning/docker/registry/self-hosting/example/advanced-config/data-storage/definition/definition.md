# Data Storage Definition

- configure the Docker Registry to store image layers and metadata in a specific directory on your local filesystem;
- this involves specifying the directory path in the `config.yml` file under the `storage` section, allowing you to control where the data is stored locally for ease of management or backup;
- **example**:

    ```yaml
    storage:
      filesystem:
        rootdirectory: /var/lib/registry  # Specify the local directory for data storage
    ```
