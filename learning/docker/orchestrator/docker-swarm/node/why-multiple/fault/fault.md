# Fault Tolerance

- if one node goes down, Swarm automatically reschedules tasks on other healthy nodes;
- ensures that your application keeps running even if part of the system fails.

**Example:**

If a node running a database replica crashes, another node can pick up the task, minimizing downtime.
