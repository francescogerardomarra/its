# Can a Container Be Suddenly Shut Down? Definition

**Question:**

If I perform a service update and the container that is going to be shut down is managing a client request, it's going to be shut down anyway or Docker Swarm waits that it finishes to manage the request?

**Answer:**

> In Docker Swarm, during a rolling update, a container that is currently handling a **client request** can be **suddenly shut down** if you don’t take specific steps to prevent disruption.

**Details:**

When Swarm replaces an old container:

1. it **sends a `SIGTERM` signal** to tell the app to shut down;
2. if the app **doesn’t handle that gracefully**, the container is forcefully stopped after a short period (`10s` by default);
3. this can **kill active connections or requests**, leading to:
    - dropped HTTP requests;
    - lost messages;
    - partially completed operations.
