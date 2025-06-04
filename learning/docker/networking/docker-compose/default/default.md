# Default Networks

- when you run a Docker Compose project without explicitly defining a network, Docker automatically creates a default network for your services;
- **it's a user-defined bridge network**.
- when you run `docker-compose up`, Docker Compose does a few things:
  1. creates a new bridge network, named like this:

       ```commandline
       <project_name>_default
       ```

       - `project_name` is usually the name of the folder where your `docker-compose.yml` lives;
       - you can override it with `-p` (`docker-compose -p <custom_project_name> up`).
  2. connects each service container in your `docker-compose.yml` to that network;
  3. sets up automatic DNS resolution between containers in that network.


- containers can talk to each other using just the service name.
