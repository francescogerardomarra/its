# Can TLS Encryption and Basic Auth Be Used Together?

- yes, TLS encryption and Basic Authentication can be used together in a self-hosted registry;
- in fact, they complement each other rather than being mutually exclusive;
- TLS encrypts the entire HTTP request and response, including the Authorization header used by Basic Authentication;


- this prevents credentials from being exposed in transit;
- therefore, TLS provides the necessary security layer for using Basic Authentication safely;
- many organizations set up a private Docker registry for their container images;


- they secure the registry with TLS to encrypt data transfers and add Basic Authentication for access control;
- **example**: a Docker registry can be configured to require users to authenticate using docker login (which uses Basic Authentication) while ensuring all communication is over HTTPS.