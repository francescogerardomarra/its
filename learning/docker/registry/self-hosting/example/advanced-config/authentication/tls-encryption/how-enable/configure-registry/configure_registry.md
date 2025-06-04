# Configure the Docker Registry

- the `config.yml` file is the main configuration file for the Docker registry, where all critical settings (e.g., storage, authentication, network, and TLS) are defined;
- if you are using the official Docker Registry image (`registry:2`), the `config.yml` file is typically located inside the container at `/etc/docker/registry/config.yml`;
- to enable TLS, add a section under the `http` key in the `config.yml` file to define the `tls` settings:
  
    ```yaml
    http:
      addr: :5000  # The port on which the registry listens
      tls:
        certificate: /path/to/cert.crt  # Path to the SSL/TLS certificate
        key: /path/to/cert.key          # Path to the corresponding private key
    ```
  

- these paths should point to the certificate and private key files on the host system;
