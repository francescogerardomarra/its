# Interactive Mode

- when you run a Docker container interactively (e.g., with `docker run -it`) and in attached mode (**without** `-d` flag) you're attached to its terminal session;
- this means your keyboard input is going directly to the container's shell or process.

**Question:**

> But what if you want to leave the container running in the background and return to your host terminal without stopping the container?

**Answer:**

- press this key combination:

    ```text
    Ctrl + P followed by Ctrl + Q
    ```

- this tells Docker: "detach my terminal from this container, but keep the container running in the background".

**Example:**

1. run this command in a terminal:

    ```commandline
    docker run -it --name my-ubuntu ubuntu
    ```
  
    output:
  
    ```commandline
    root@8628922e80a5:/#
    ```

2. detach from container:

    ```commandline
    Ctrl + P followed by Ctrl + Q
    ```
    
    output:
    
    ```commandline
    root@8628922e80a5:/# 
    andrea@avolpe-pc:~$ 
    ```

3. check if the container is still running:

    ```commandline
    docker ps
    ```
    
    output:
    
    ```commandline
    CONTAINER ID   IMAGE                  COMMAND                  CREATED              STATUS              PORTS                                NAMES
    8628922e80a5   ubuntu                 "/bin/bash"              About a minute ago   Up About a minute                                        my-ubuntu
    ```
