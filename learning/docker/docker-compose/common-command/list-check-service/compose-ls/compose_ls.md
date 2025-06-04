# `docker compose ls`

This command is used to list all the Docker Compose projects that are running on your system.

**Example:**

1. start 2 different Docker Compose projects, located in 2 different folders on your machine using the command:

    ```commandline
    docker compose up
    ```

2. check all the Docker Compose projects running on your sisntem

    ```commandline
    docker compose ls
    ```
    
    output:
    
    ```commandline
    NAME                STATUS              CONFIG FILES
    compose-example     running(5)          /home/andrea/Documents/Corsi/Docker/compose-example/docker-compose.yml
    desktop             running(4)          /home/andrea/Desktop/docker-compose.yml
    ```
    
    the `CONFIG FILES` colums shows the path to the `docker-compose.yml` config files used to define the projects.
