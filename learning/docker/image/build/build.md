# Build an Image

Building a Docker image involves creating a `Dockerfile` that defines the instructions for the image and then using the docker build command to build the image:

1. in the project directory, create a file named `Dockerfile` (no extension);
2. add instructions to define your image, for example:

    ```dockerfile
    # Use an official Python runtime as a base image
    FROM python:3.9-slim
    
    # Set the working directory in the container
    WORKDIR /app
    
    # Copy the current directory contents into the container at /app
    COPY . /app
    
    # Install any required dependencies
    RUN pip install --no-cache-dir -r requirements.txt
    
    # Make port 80 available to the world outside this container
    EXPOSE 80
    
    # Define the command to run the application
    CMD ["python", "app.py"]
    ```
   
3. run the docker build command from the directory containing the `Dockerfile`:

    ```commandline
    docker build -t my-app:1.0 .
    ```
   
   - `-t my-app:1.0`: tags the image with a name (my-app) and version (1.0);
   - `.` specifies the current directory as the **build context**;
   - all the build context contents are available to Docker for the build;
   - each instruction in the `Dockerfile` refers to it;
   - for example, `COPY app.py /app` Docker looks for `app.py` in the build context;
   - the `Dockerfile` can be in a different location from build context (the location must be specified in the build command);
   - but is a common practice to have the `Dockerfile` within the build context.
4. check that your image was created:

    ```commandline
    docker images
    ```
   
   - this command shows all the images in your machine.