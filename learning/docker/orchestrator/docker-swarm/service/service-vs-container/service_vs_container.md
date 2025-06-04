# Service VS Container

In this chapter, we see the differences of services and containers.

**Table:**

| Concept          | **Service**                                               | **Container**                            |
|------------------|-----------------------------------------------------------|------------------------------------------|
| **What it is**   | a definition or **desired state** of a task in the Swarm. | a **running instance** of an image.      |
| **Role**         | orchestrates and manages containers.                      | executes a specific application/process. |
| **Managed by**   | Docker Swarm (via Docker Engine).                         | Docker Engine directly.                  |
| **Scale**        | can represent **many containers**.                        | a single unit (one process/image).       |
| **Self-healing** | yes, Swarm re-creates failed tasks.                       | no, if it crashes, it stays down.        |
| **Examples**     | a service running 5 replicas of NGINX.                    | one running NGINX container.             |
