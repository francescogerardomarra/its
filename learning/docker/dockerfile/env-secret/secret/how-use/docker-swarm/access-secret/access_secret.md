# Access the Secret in a Container

- **secrets as files**: in a running container, secrets are securely stored as files under the `/run/secrets/` directory;
- **accessing secrets**: you can read a secret using commands like `cat /run/secrets/db_password`;
- **improved security**: unlike environment variables, secrets are not exposed in the container's environment, reducing the risk of accidental leaks;
 

- **access control**: only processes running inside the container with the right permissions can access the secrets, preventing unauthorized access from outside.

**Command to Access the Secret in a Container:**

```commandline
cat /run/secrets/db_password
```
