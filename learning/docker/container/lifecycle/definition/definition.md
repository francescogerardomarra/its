# Container Lifecycle

- when you run a Docker container using the command `docker run`, several things happen depending on the configuration and the nature of your application;
- here's what generally happens after running a container and when the program finishes:

  1. **container starts**: when you run the command (e.g., `docker run --name my-running-app dockerfile-hello-world`), Docker creates a new container instance from the specified image (`dockerfile-hello-world`), assigns it a unique ID, and starts executing the command defined in the `CMD` section of your Dockerfile (in this case, running your Java program);
  2. **program execution**: 
     - the Java program (e.g., `MyProgram.jar`) runs inside the container;
     - during this time, the container is in the "running" state;
     - some programs, like a REST API server, never terminate since they are always awake, listening for new coming requests;
     - **the container remains running as long as the program continues to execute**.
  3. **program finishes**: once your Java program completes its execution (either by reaching the end of its logic or encountering an exit condition), the following happens:
     - **container stops**: 
       - the container will stop running because there are no longer any processes left to execute;
       - Docker detects that the main process has exited.
     - **exit status**: the container will have an exit status that indicates whether the program completed successfully (exit code 0) or if it encountered an error (non-zero exit code).
