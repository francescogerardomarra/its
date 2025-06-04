# How to Enable It

- use a tool like `htpasswd` (from the Apache HTTP Server package) to create a file containing usernames and hashed passwords:
  - example command:
  
    ```bash
    htpasswd -Bc /path/to/auth/htpasswd username
    ```
    
    - `-B`: use bcrypt for hashing (more secure than other methods);
    - `-c`: create a new file (omit this if you want to add users to an existing file).
- configure the registry to use this file by updating the `auth` section in the registry's configuration file (`config.yml`):
  
  ```yaml
  auth:
    htpasswd:
      realm: "Registry Realm"
      path: /path/to/auth/htpasswd
  ```
