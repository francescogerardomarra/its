# Purpose

- when you run `docker build`, Docker packages everything in your project directory (or the specified build context) and sends it to the Docker daemon;
- the `.dockerignore` file controls which files and directories are **excluded** from the build context sent to the Docker daemon;
- if your build context contains large files or directories (like `node_modules` or `.git`), Docker will package and transfer all of that data, even if they aren't used in the image, this can slow down the build process;
 

- by using a `.dockerignore` file, you can prevent unnecessary files from being included in the build context, this makes the build process **faster** and less resource-intensive;
- this exclusion happens **before** Docker executes any instructions (like `COPY`) in the `Dockerfile`;
- **for example**:
    - if your `Dockerfile` includes the command `COPY . /app`, any files or directories listed in the `.dockerignore` file will **not** be present in the build context before sent to the Docker daemon;
    - as a result, only the remaining files (those not ignored) will be copied from the build context into the Docker image.
