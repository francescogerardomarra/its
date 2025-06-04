# Lifecycle

- tasks are immutable, if a task fails or is stopped, Swarm creates a new task to replace it;
- this is part of how Swarm maintains service availability;
- suppose you run (to change the image version number):

    ```commandline
    docker service update --image nginx:1.25 my-nginx
    ```

  - Swarm does not go and modify the image of the old tasks;
  - instead, it:
    - sets the desired state of old tasks to shut down;
    - create new tasks with the new image (`nginx:1.25`);
    - schedule them on nodes.

**Task lifecycle:**

A task typically goes through these states:

1. `NEW`: task is created by the Swarm manager;
2. `PENDING`: waiting for scheduling;
3. `ASSIGNED`: task has been assigned to a specific node;
4. `PREPARING`: node is preparing to run the task;
5. `STARTING`: container is starting;
6. `RUNNING`: container is running;
7. optional states like `FAILED`, `SHUTDOWN`, or `COMPLETE` if the container exits.
