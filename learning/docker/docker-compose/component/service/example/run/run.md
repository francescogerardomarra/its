# Run

Enter the root project folder with a terminal and run this command:

```commandline
docker compose up -d
```

**Above command explanation:**

- it starts all the services defined in the `docker-compose.yml` file in detached mode, meaning the containers run in the background;
- without the `-d` flag, the containers would run in the foreground, and the logs would be streamed directly to the terminal, blocking the command until interrupted;
- if you run `docker compose up` without the `-d` flag and press `Ctrl+C`, it will stop all the running services and shut down the containers.
