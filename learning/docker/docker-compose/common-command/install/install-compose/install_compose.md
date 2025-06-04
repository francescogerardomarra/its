# Install Docker Compose

- update the package list to ensure you have the latest package information:

  ```bash
  sudo apt update
  ```  

- install Docker if it is not already installed:

  ```bash
  sudo apt install -y docker.io
  ```  

- install the `docker-compose-plugin`, which enables the `docker compose` command:

  ```bash
  sudo apt install -y docker-compose-plugin
  ```  

- verify the installation by checking the Docker Compose version:

  ```bash
  docker compose version
  ```  
