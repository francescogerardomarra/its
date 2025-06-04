# Persist Container Layer to Image (`docker commit`)

- in some cases, you may want to save the changes you've made to an image's filesystem through a container;
- you can do this by creating a new image that includes the container's changes as a new read-only layer;
- the `docker commit` command lets you create this new image from the container.

**Example:**

Imagine to run a container Ubuntu and add a `/test/myfile.txt` to it, you want to create a new image, that has this file:

1. open a terminal and run the container:

    ```commandline
    docker run -it ubuntu bash
    ```

2. add a new file `/test/myfile.txt` containing the string `This is a test file`:

    ```commandline
    echo "This is a test file" > test/myfile.txt
    ```

3. check if the file exists and contains the string:

    ```commandline
    cat /test/myfile.txt
    ```

    output:
    
    ```commandline
    This is a test file
    ```

4. open **another** terminal and find the `container-id`:

    ```commandline
    docker ps
    ```
    
    output:
    
    ```commandline
    CONTAINER ID   IMAGE                 COMMAND                  CREATED         STATUS              PORTS                                NAMES
    04a56ce05e59   ubuntu                "bash"                   9 minutes ago   Up 3 minutes                                             awesome_chandrasekhar
    ```

5. commit the container to a new image:

    ```commandline
    docker commit 04a56ce05e59 my-custom-image:1.0
    ```

6. verify your image is present:

    ```commandline
    docker images
    ```
    
    output:
    
    ```commandline
    REPOSITORY                       TAG           IMAGE ID       CREATED         SIZE
    my-custom-image                  1.0           c6a14a6ced37   8 seconds ago   78.1MB
    ```

7. instantiate a container from the new image and run a bash within it:

    ```commandline
    docker run --name my-custom-container -it my-custom-image:1.0 bash
    ```

8. open **another** terminal and check if both containers are still running:

    ```commandline
    docker ps
    ```
    
    output:
    
    ```commandline
    CONTAINER ID   IMAGE                 COMMAND                  CREATED          STATUS              PORTS                                NAMES
    34f67776f777   my-custom-image:1.0   "bash"                   34 seconds ago   Up 34 seconds                                            my-custom-container
    04a56ce05e59   ubuntu                "bash"                   14 minutes ago   Up 8 minutes                                             awesome_chandrasekhar
    ```

9. check within `my-custom-container` bash if the `/test/myfile.txt` is still present:

    ```commandline
    cat /test/myfile.txt 
    ```
    
    output:
    
    ```commandline
    This is a test file
    ```
