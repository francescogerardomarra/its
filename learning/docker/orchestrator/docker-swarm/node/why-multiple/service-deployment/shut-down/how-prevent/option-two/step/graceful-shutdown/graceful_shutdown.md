# Graceful Shutdown Handler

- one approach is to handle the `SIGTERM` signal (sent by Docker during shutdown) directly in your application code;
- in the shutdown handler, you can stop your custom thread executor (if you’re using one), so it no longer accepts new background tasks;
- this allows the current in-flight tasks or HTTP requests to finish, while preventing new ones from being processed;


- Swarm may detect, via [health checks], that the container is shutting down and eventually stop routing traffic to it; <!-- TODO link to next point health checks -->
- however, there is a time gap between when the application stops accepting new requests and when Swarm reacts to the failed health check;
- during this gap, a client request could still be routed to the shutting-down container and be rejected or fail, resulting in an error;
 

- therefore, **this strategy is not fully reliable for achieving zero downtime**.
