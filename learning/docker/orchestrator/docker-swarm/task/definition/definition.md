# Task Definition

- a task represents a **single container** running on a Swarm node;
- it is created by the Swarm manager when you deploy a service;
- it contains both:
  - the container specification (image, commands, etc.);
  - metadata (like the node it should run on, desired state).


- when the task is running, it manages the actual container instance based on the container specification.
