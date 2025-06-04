# Port Mapping

- you can map container ports to host ports with `-p` or `--publish`;
- when you run a service inside a Docker container (like a web server, database, or app), it usually listens on a port inside the container, like `80`, `5000`, or `8080`;
- by default, that port is not accessible from outside the container (i.e., your local machine or browser);

 
- port mapping allows you to expose that internal container port to the outside world.

**Example:**

```commandline
docker run -p 8080:80 nginx
```

- nginx is a web server that listens on port `80` inside the container;
- `-p 8080:80` maps port `80` of the container to port `8080` of your host (your machine);
- so, you can open your browser and go to http://localhost:8080 to access the nginx server.
