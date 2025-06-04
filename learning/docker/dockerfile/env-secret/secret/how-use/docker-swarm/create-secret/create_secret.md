# Create a Secret

- with this command we create a secret named `db_password`;
- run this command within the server that is part of your Docker Swarm (i.e., where you initialized [docker swarm init](../enable/enable.md));
- `-` is a special argument that tells Docker to read the secret from standard input (stdin) instead of a file;


- Docker expects secrets to be created from a file by default;
- if you omit the `-`, Docker will look for a file named `db_password`, which is not what you want in this case.

**Command to Create a Secret Named `db_password`:**

```commandline
echo "my-secure-password" | docker secret create db_password -
```
