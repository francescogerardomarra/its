# Use AWS S3 Definition

- instead of using a local directory, you can configure the registry to use AWS S3 as a storage backend;
- this offloads image storage to AWS, providing scalability, durability, and geo-redundancy;
- you must specify the S3 bucket name, access keys, and region in the `config.yml`;
 

- S3 managed nature also ensures reduced maintenance overhead compared to local storage;
- **example**:

    ```yaml
    storage:
      s3:
        accesskey: <your-access-key>         # AWS Access Key
        secretkey: <your-secret-key>         # AWS Secret Key
        region: us-east-1                    # AWS Region (e.g., us-east-1, eu-west-1, etc.)
        bucket: my-docker-registry-bucket    # Name of the S3 bucket
        encrypt: true                        # Enable server-side encryption for stored objects
        secure: true                         # Use HTTPS for communication with S3
        v4auth: true                         # Use AWS Signature Version 4 (recommended)
        rootdirectory: /                     # Optional: Set a prefix within the bucket
    ```
