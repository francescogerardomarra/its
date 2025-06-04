# Why don't Use `docker service create` Command?

- `docker stack deploy` allows you to define and deploy multiple services, networks, and volumes from a single `docker-compose.yml` file;
- while `docker service create` can only create one service at a time;
- with `docker stack deploy`, all services are managed together as a unit, making it easier to scale, update, and remove services consistently;


- with `docker service create`, you need to manually create, link, and manage each service separately.
