# Zero Downtime

- consider you want to update a Swarm service (for example changing the image), like explained [here](../rolling/rolling.md);
- the [rolling update strategy](../rolling/rolling.md) assure zero downtime;
- **zero downtime** means your application stays available to users **throughout the update**;


- Docker Swarm helps achieve this by:
  - updating only a portion of containers at a time;
  - ensuring new containers are **up and running** before stopping old ones;
  - **load balancing** traffic across healthy containers automatically.
- so as long as there are still working containers during the update, users don’t experience any service interruption;
- **benefits**: high availability even during deployments.
