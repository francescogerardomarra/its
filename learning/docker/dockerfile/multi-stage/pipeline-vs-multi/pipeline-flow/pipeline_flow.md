# Pipeline Flow Using Multi-Stage `Dockerfile`

- a less common scenario, it's using a Dockerfile multi-stage build within the CI/CD pipeline;
- in this case, the build and test are run within a temporary Docker container and completely managed by the Dockerfile.

**Steps:**

1. a developer pushes code to a version control system (e.g., GitHub, GitLab);
2. the pipeline detects the change and starts the build process;
3. **a multi-stage Dockerfile is used to build the application inside the container**;<br/><br/>
4. **automated tests run inside the container to check for errors and ensure functionality**;
5. the final optimized Docker image is created, containing only the necessary files;
6. the image is pushed to a container registry (e.g., Docker Hub, AWS ECR);<br/><br/>
7. the application is deployed to a server, cloud, or Kubernetes cluster, for example:
    1. the pipeline connects to a server and pulls the latest image;
    2. it instantiates a container from the image and starts the application.<br/><br/>
8. post-deployment tests run to verify the deployment;
9. the pipeline reports success or failure, alerting developers if needed.  
