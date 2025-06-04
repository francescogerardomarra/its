# Port Binding Definition

- when you run a Docker container, it's like running a mini virtual computer;
- that container might run a web app, an API, a database, or anything else that listens on a network port (like port `80` or `8080`);
- but that container is isolated from your actual computer (called the host);
 

- so if you want to access the service running inside the container from your browser or another app on your host machine, you need to bind a port from your host to the container;
- when your machine (the Docker host) is a **server**, port binding does more than just let you access a service locally:
  - **it lets the outside world reach the container through your host machine’s public IP**.
