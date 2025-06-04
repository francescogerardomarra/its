# Networks Definition

- a network in Docker Compose is a way to define communication between containers within a Docker environment;
- it allows containers to find and communicate with each other by name, without needing to know their IP addresses;
- networks help isolate services from each other, improving security and preventing unwanted communication;


- by default, Docker Compose creates a network for every project, but you can define custom networks to have more control;
- you can specify the network mode as `bridge`, `host`, or `none`, depending on how you want containers to connect;
- networks can be shared across multiple services by defining them at the top level of the `docker-compose.yml` file;


- it is possible to define external networks to connect with networks created outside of the Compose project;
- using networks in Docker Compose allows fine-grained control over how services interact with each other and the outside world.
