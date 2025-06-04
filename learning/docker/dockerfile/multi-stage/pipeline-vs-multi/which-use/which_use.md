# Which One Should You Use?

**Use a pipeline to generate the artifact + `Dockerfile` for the image if:**
- you need to reuse the build artifact across multiple environments;
- your app doesn’t always run in a container (e.g., a JAR can run on a VM);
- you want faster builds by caching dependencies separately;
- you need more control over the build and deployment process.

**Use a multi-stage `Dockerfile` for both artifact and image if:**
- your app is fully containerized (e.g., microservices, Kubernetes);
- you want a self-contained, simple build process inside Docker;
- your CI/CD pipeline should only build, push, and deploy the container;
- you don’t need to reuse the artifact outside the container.
