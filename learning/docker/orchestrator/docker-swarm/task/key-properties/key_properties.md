# Key Properties

A task has the following properties:
- **task ID**: unique identifier;
- **state**: one of `new`, `assigned`, `running`, `failed`, `shutdown`, etc.
- **desired state**: what Swarm wants the task to be (e.g., `running`);
 

- **node assignment**: the node where it will run;
- **containerSpec**: details like image, command, env variables.
