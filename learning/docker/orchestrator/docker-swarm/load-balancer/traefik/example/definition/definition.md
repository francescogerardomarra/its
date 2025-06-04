# Example Definition

- the following `docker-compose.yml` file deploys **Traefik** as a **reverse proxy, load balancer and automatic HTTPS certificate manager** in a Docker Swarm cluster;
- it configures Traefik to discover services via Docker, handle HTTP and HTTPS traffic, and store **Let's Encrypt** certificates in a persistent volume;
- in this example, Traefik substitutes the Docker Swarm routing mesh for HTTP/HTTPS traffic;


- to create the external Docker network named traefik, run the following command:

    ```commandline
    docker network create --driver=overlay --attachable traefik
    ```

    - **if we omit the `--attachable` flag**, only Docker Swarm services can connect to the overlay network, not standalone containers;
    - **you don’t need `--attachable` if you're only using Docker Swarm services**;
    - however, adding it provides extra flexibility and that’s often worth it in real-world setups.
