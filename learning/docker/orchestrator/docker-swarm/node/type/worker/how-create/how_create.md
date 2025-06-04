# How to Create It

1. **create a manager node**: once you [created a manager node] you received this output: <!-- todo: link to creating a manager node -->

    ```commandline
    Swarm initialized: current node (wbkfxctwut6tuvtiru0s2r5uu) is now a manager.
    
    To add a worker to this swarm, run the following command:
    
        docker swarm join --token SWMTKN-1-5mpo4vepc5ue76n43vw8h1vw0z68z7mfqzf2t1hm7ot7z5l5k9-ajs5sv5f0ljefos06bmx5sgbi 192.168.1.11:2377
    
    To add a manager to this swarm, run 'docker swarm join-token manager' and follow the instructions.
    ```
2. **join the swarm**: on another node you add a worker to the Swarm using the previous join token provided by a manager:

    ```commandline
    docker swarm join --token <worker-token> <manager-ip>:2377
    ```

3. **receive tasks**: the manager schedules tasks and distributes them to workers;
4. **report status**: workers constantly report health and task status back to the manager.
