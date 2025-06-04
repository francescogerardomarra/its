# Rolling Update Begins (If Applicable)

Unless you specify otherwise, Docker Swarm performs a **rolling update**, this means:
- a few (or one) running tasks (containers) are stopped;
- new containers are started with the updated configuration (e.g., new image, env vars);
- this continues in batches until all tasks are updated.
