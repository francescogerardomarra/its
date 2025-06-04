# Why `docker compose up` Doesn't Work With Swarm 

* even if Swarm is initialized (`docker swarm init`), `docker compose up`:
  * **ignores Swarm mode**;
  * creates normal Docker containers (not Swarm services);
  * cannot leverage features like **replicas, rolling updates, placement constraints**, etc.
* so, to use the Swarm orchestration features:
  * you must use `docker stack deploy -c docker-compose.yml <stack-name>` (see [here](../../../common-command/stack/deploy/deploy.md)).
