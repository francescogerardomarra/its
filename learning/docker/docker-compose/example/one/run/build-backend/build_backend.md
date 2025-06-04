# Build the Backend Image

- we're using a custom pre-built image for the backend;
- it would have been easier to let Docker Compose build the image automatically;
- however, we chose this approach to demonstrate how to use a locally available image within a Docker Compose service.

**Steps:**

1. open within the terminal the `backend/` folder within the downloaded project;
2. run the following command:

    ```commandline
    docker build -t compose-backend:1.0 .
    ```

3. if you run this command, you'll see the built image that Docker Compose will use for creating the stack:

    ```commandline
    docker images
    ```
