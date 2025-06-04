# Health Checks

- [health checks]() run the same way in Docker Swarm as in standalone Docker, but Swarm doesn't act on the results automatically; <!-- TODO: link to docker compose chapter -->
- the container image or service defines the health check rules, and the Docker daemon runs the health check command inside the container at set intervals;
- after sending the `SIGTERM`, Swarm might detect an 'unhealthy' status if the container transitions before it fully stops, but it will not take any action based on that status; 


- once the task is marked as unhealthy, **Swarm does not stop routing traffic to that container**;
- client requests can still be sent to that container, potentially leading to response errors, **making this approach not fully reliable for zero-downtime updates**;
- **health checks are useful to understand when the new deployed containers are ready and traffic can be send to them**.
