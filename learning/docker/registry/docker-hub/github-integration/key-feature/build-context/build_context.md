# Build Context Configuration

- you can specify the **build context**, which is the directory in your GitHub repository that contains the `Dockerfile` and other required files;
- the **build context** is set in **Docker Hub**, not in GitHub, during the automated build setup process;
- if your `Dockerfile` is located in the root directory of your repository, the build context should be set to `/` (default);


- if your `Dockerfile` is located in a subdirectory of your GitHub repository (e.g., `app/`), you need to specify that subdirectory (e.g., `app/`) as the build context in Docker Hub;
- setting the correct build context ensures that only the necessary files from your repository are sent to the Docker daemon for building the image.  
