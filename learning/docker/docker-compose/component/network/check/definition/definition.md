# Check Networks Definition

- open your terminal and navigate to the directory containing your `docker-compose.yml` file;
- make sure the Docker Compose environment is running by executing `docker-compose up -d`;
- list all Docker networks by running the command `docker network ls`;


- find the network name related to your Docker Compose project, which usually follows the pattern `<project_name>_<network_name>`;
- inspect the specific network by running `docker network inspect <network_name>` to see details such as containers connected, IP addresses, and configurations;
- check containers attached to the network using the same inspect command and look for the `Containers` section;
 

- alternatively, view container-specific network details by running `docker inspect <container_name>` and examining the `Networks` section.
