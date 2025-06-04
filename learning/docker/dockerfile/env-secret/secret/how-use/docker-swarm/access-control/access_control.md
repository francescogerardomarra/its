# Access Control

- secrets can only be accessed by services that are explicitly granted access to them using the `--secret` flag when the service is created or updated;
- secrets cannot be shared between non-authorized services;
- when one replica is removed, the secret is removed from memory on that node only if no other container on that node needs it.
