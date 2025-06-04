# Push the Tag to Repository

- it's not possible to push a tag to the repository, since it's just a label;
- when you "push the tag," you're actually pushing the entire image along with its tag to the repository;
- if you push an image and later add a new tag to it without making any changes to the image, Docker will only upload the new tag's metadata to the remote repository, not the image layers;


- if the repository already has some of the image's layers (due to an earlier push), only the new or changed layers will be uploaded.

**Push:**

1. log in to the Docker Registry (if not already logged in):

    ```commandline
    docker login
    ```

   docker login command connects to the default registry (Docker Hub) unless you specify a different registry;
2. **or** log in to a custom registry:

    ```commandline
    docker login myregistry.com
    ```
   
   3. push the image to the repository:

       ```commandline
       docker push username/repo-name:tag
       ``` 

       - on registries like Docker Hub, repositories are typically owned by users or organizations;
       - including the `username` specifies whose repository the image belongs to;
       - in many cases, the `username` in Docker image tags is often the company name;
       - before pushing the image to a Docker registry (e.g., Docker Hub), you must tag it with the `username` (or namespace) to specify where the image will be stored:

           ```commandline
           docker tag repo-name:tag username/repo-name:tag
           ```  
   
       - `username` in the image tag is just part of the repository path, while the `username` in docker login is used for authentication.
    