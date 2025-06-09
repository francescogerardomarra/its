# Install Docker Engine

Follow these steps to install Docker Engine on your Linux machine, which is the core component of Docker responsible for building, running, and managing containers in a lightweight, portable, and consistent environment:

1. update your system:

    ```commandline
    sudo apt update
    sudo apt upgrade -y
    ```

2. install required packages:
   - these allow `apt` to use repositories over HTTPS:

       ```commandline
        sudo apt install apt-transport-https ca-certificates curl software-properties-common lsb-release -y
       ```

3. add Docker's official GPG key:

    ```commandline
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
    ```

4. set up the Docker stable repository:

    ```commandline
    echo \
      "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] \
      https://download.docker.com/linux/ubuntu \
      $(lsb_release -cs) stable" | \
      sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
    ```

5. update the package index:

    ```commandline
    sudo apt update
    ```

6. install Docker engine:

    ```commandline
    sudo apt install docker-ce docker-ce-cli containerd.io -y
    ```

7. check Docker installation:

    ```commandline
    sudo docker --version
    ```

8. run Docker as a non-root user:

   - to avoid having to use `sudo` every time you run a Docker command:
     - add your user to the `docker` group:

         ```commandline
         sudo usermod -aG docker $USER
         ```

   - then, **apply the group change** by either:
     - logging out and logging back in (recommended);
     - **or** running this command to refresh your group membership in the current terminal session:

         ```commandline
         newgrp docker
         ```
   - after doing this, you’ll be able to run Docker commands **without `sudo`** permanently.

9. verify Docker installation:

   - test that Docker can run containers properly:

     ```commandline
     docker run hello-world
     ```

       - this will download and run a small test container;
       - the `hello-world` image, which is publicly available on Docker Hub;
       - if everything works, you'll see a message confirming that Docker is installed correctly.
