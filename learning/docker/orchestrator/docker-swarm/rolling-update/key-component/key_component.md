# Key Components

- when performing a rolling update in Docker Swarm, the following options control the process;
- the following update-related options are stored in the Docker Swarm service configuration and remain in effect for future updates unless explicitly changed (`--rollback` is a command, not a configurable option, and is not stored);
- **this means that once these options are set, they will automatically apply to future updates without needing to be specified again in each command**:

  - `--update-parallelism`: number of containers to update at once;
  - `--update-delay`: delay between updating groups of containers;
  - `--update-failure-action`: what to do if an update fails (`pause`, `continue`, or `rollback`);
  - `--update-monitor`: 
    - duration to monitor a task for failure after it starts; 
    - if the container crashes or becomes unhealthy during this period, the update is considered failed.
  - `--rollback`: automatically revert to the previous version if something goes wrong.
