# Why Can They Be Used to Assure Zero Downtime?

Both roll forward and roll back assure zero downtime:

- traffic is moved to new containers without the client noticing any difference on the service;
- the old containers are never shutted down until all the traffic is routed to new containers;
- this allows old containers to finish all their tasks;
 

- this allows also fast roll backs in case something goes wrong (old containers are still running);
- if you remove the old containers only after you're certain that all traffic has been routed to the new containers and the old ones are no longer handling any requests, then you don’t need to implement [graceful shutdown logic](../../../option-two/step/graceful-shutdown/graceful_shutdown.md) in your application code;
- **in practice, it’s hard to guarantee that no connections are still open unless**:
  - you use connection draining or health checks to make sure a container stops receiving new requests before it is shut down;
  - your app doesn’t use long-lived connections like WebSockets, large file uploads, or streaming data, which can stay open even after traffic is rerouted;
  - you wait a short amount of time after rerouting traffic before shutting down the container, giving any remaining requests time to finish.
