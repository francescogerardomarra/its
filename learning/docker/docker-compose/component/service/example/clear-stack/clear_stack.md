# Clear the Stack (Optional)

Open a terminal and run this command:

```commandline
docker compose down
```

**Above command explanation:**

- stops all running containers defined in the `docker-compose.yml` file;
- removes the containers from the Docker environment (running `docker ps -a` you will **not** see those containers listed);
- deletes any networks created by the Docker Compose file;


- removes volumes if specified with the `-v` flag;
- does not delete images or orphaned containers;
- orphaned containers are containers that were created by services removed from the `docker-compose.yml` file but still exist from previous runs of Docker Compose;


- can be used with the `--rmi` flag to remove images created by the Docker Compose file;
