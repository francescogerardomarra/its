# Why Use Secrets

Read the [before proceeding](../before-proceed/before_proceed.md) section, before reading this chapter:

- environment variables are not secure, they can be seen using `docker inspect my_container`;
- inside the container, anyone can access environment variables using `printenv | grep DB_PASSWORD` or by reading `/proc/self/environ` (a file that contains the process's environment variables);
- sensitive data can be exposed if an application logs its environment variables, either intentionally during normal operation or unintentionally in error messages when it crashes;


- Docker secrets are stored as files inside the container at `/run/secrets/` folder;
- a file containing the password for database can be `/run/secrets/db_password/db_password` (viewable with `cat /run/secrets/db_password`);
- secrets are not exposed in `docker inspect`, unlike environment variables;


- file-based secrets are more secure, only the container user or root can access them.  
