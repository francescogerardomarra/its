# Automated Builds

- automatically build Docker images from code repositories (e.g., GitHub or Bitbucket) whenever changes are pushed;
- the automated builds feature allows Docker Hub to automatically build Docker images from a Dockerfile stored in a linked code repository, such as GitHub or Bitbucket;
- this means you don't need to manually build and push the image each time your code changes.

**How it Works:**

1. **linking a repository**:
   - you connect your GitHub or Bitbucket repository to Docker Hub;
   - Docker Hub monitors the repository for changes.

2. **defining a Dockerfile**:
   - the repository must include a **Dockerfile** that specifies how to build the Docker image.

3. **triggering builds**:
   - when you push changes to the repository (e.g., updating code or modifying the Dockerfile), Docker Hub automatically triggers the build process.

4. **creating and storing images**:
   - Docker Hub builds the image and stores it in the specified repository on Docker Hub;
   - you can tag the images to manage different versions.
