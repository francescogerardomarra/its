# Services Introduction

- **service** in Docker Compose defines a containerized application that runs in an isolated environment;
- each service corresponds to a container (or more in case of [replicas](../../../common-command/scale-service/compose-up-scale/compose_up_scale.md)), specifying its image, dependencies, network settings, volumes, and environment variables;
- multiple services can be defined in a `docker-compose.yml` file to work together as part of an application [stack](../stack/definition/definition.md);


- services can be built from a `Dockerfile` or use pre-built images from Docker Hub or other registries;
- they can be configured with restart policies, resource limits, and logging options;
- dependencies between services can be managed using the `depends_on` option, ensuring proper startup order;


- networking settings allow services to communicate using custom networks or default bridge mode;
- volumes can be attached to services for persistent data storage across container restarts;
- environment variables and configuration files can be injected to customize service behavior;


- scaling of services can be managed using the `replicas` option when using Docker Swarm mode.
