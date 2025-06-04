# How Enable It

- to enable Docker Swarm, you must initialize the swarm on one node (this will be your manager node):

    ```commandline
    docker swarm init
    ```

- this command will:
  - enable swarm mode;
  - make the current machine a manager;
  - display a command to use for other nodes to join as workers:

    ```commandline
    Swarm initialized: current node (mfme9lhyt3620ts8arp9itdmg) is now a manager.
    
    To add a worker to this swarm, run the following command:
    
        docker swarm join --token SWMTKN-1-1lxs9tpe0eewhhv13ndfxshwhmadvq1tg488iib225tvdt3dg5-akvn726yqx8kcikbkhourvdgx 192.168.1.11:2377
    
    To add a manager to this swarm, run 'docker swarm join-token manager' and follow the instructions.
    ```
    <!-- todo: explain this command within the proper section: docker swarm join --token SWMTKN-1-1lxs9tpe0eewhhv13ndfxshwhmadvq1tg488iib225tvdt3dg5-akvn726yqx8kcikbkhourvdgx 192.168.1.11:2377
