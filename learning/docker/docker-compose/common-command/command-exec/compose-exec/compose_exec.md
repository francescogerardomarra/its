# `docker compose exec <service> <command>`

- this command is used to run a command inside a running service container defined in a `docker-compose.yml` file;
- if `<service>` has more than one replica, Docker Compose will choose which replica to run the `<command>` in (usually the first one).

**Example:**

- consider running this [hello-world](../../list-check-service/compose-top/compose_top.md) Docker Compose project;
- open a terminal within the project root folder and launch this command to run the bash within `my-service` container:

    ```commandline
    docker compose exec my-service bash
    ```

- output (we ran `ls` command inside the bash shell):

    ```commandline
    bash-4.4# ls
    Hello.class  Hello.java  bin  boot  dev  etc  home  lib  lib64	media  mnt  opt  proc  root  run  sbin	srv  sys  tmp  usr  var
    ```
