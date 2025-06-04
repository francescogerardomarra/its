# Each Node Has an Instance

- **each node runs its own instance of the Traefik proxy**;
- this means that every node in your Docker Swarm cluster will have its own dedicated Traefik instance;
- making routing more localized and independent;


- **each node hit by a client request will have a Traefik service, which can handle the request**;
- without **global mode**, if the client hits a node that isn't running Traefik, the request will fail or be dropped;
- because there's no local Traefik instance to receive and route the traffic.
