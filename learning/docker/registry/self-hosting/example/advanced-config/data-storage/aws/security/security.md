# Security

- using S3 introduces the need for securing access through IAM policies (which define who can perform specific actions on AWS resources), access keys, or roles;
- ensure proper permissions are configured to prevent unauthorized access to the images stored within your Docker Registry;
- additionally, enable S3 encryption (SSE) to protect your data while it is stored in the bucket (at rest), and use HTTPS to secure data while it is being transferred over the network (in transit).
