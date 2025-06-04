# Create a Secret

- create a secret taking it from a file;
- **the command should be executed from a manager node in the Swarm;**
- `docker secret create db_password`: this creates a new Docker secret named `db_password`;


- `-`: this tells Docker to read the secret content from standard input (stdin);
- `< my_password.txt`: this redirects the contents of `my_password.txt` into standard input (stdin), effectively passing the file's contents to Docker.

**Command to Create a Secret Named `db_password`:**

```commandline
docker secret create db_password - < my_password.txt
```
