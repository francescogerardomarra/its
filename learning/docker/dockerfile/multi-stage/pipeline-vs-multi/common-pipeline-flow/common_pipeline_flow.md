# Common Pipeline Flow

1. a developer pushes code to a version control system (e.g., GitHub, GitLab);
2. the pipeline detects the change and starts the build process;
3. the code is compiled (if needed) and dependencies are installed;<br/><br/>
4. automated tests run to check for errors and ensure functionality;
5. a build artifact is created (e.g., a JAR, binary, or frontend bundle);
6. Docker image is built using the artifact;<br/><br/>
7. the image is pushed to a container registry (e.g., Docker Hub, AWS ECR);
8. the application is deployed to a server, cloud, or Kubernetes cluster, for example:
   1. the pipeline connects to a server and pulls the latest image;
   2. it instantiates a container from the image and starts the application.<br/><br/>
9. post-deployment tests run to verify the deployment;
10. the pipeline reports success or failure, alerting developers if needed.  
