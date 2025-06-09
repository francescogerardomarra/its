# `docker compose down`

- this command stops all running containers defined in your `docker-compose.yml` file;
- it removes the containers (they won't exist anymore);
- it removes the default and custom networks that were created for those containers;


- it removes any [anonymous volumes](../../../../volume/mount/type/anonymous/how-work/how_work.md) (named volumes will stay);
- if you use `--volumes` or `-v` flag, also named volumes will be removed.
