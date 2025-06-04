# Tag

- **create a new tag for an existing image**:
  - `<target_repository>:<target_tag>` specifies the existing image to which you want to add a new tag;
  - `<new_repository>:<new_tag>` represents the new tag you want to assign to the existing image.

    ```commandline
    docker tag <target_repository>:<target_tag> <new_repository>:<new_tag>
    ```

- **tag an image for pushing to a specific registry**:
  - prepare for registry-specific workflows: by tagging the image with the format `username/repository:tag`, you ensure the image is correctly associated with your account on the target registry (e.g., Docker Hub, AWS ECR, or GCR);
  - simplify pushing to the desired registry: this tag structure allows you to specify where the image will be stored, making it straightforward to push the image using a single command, such as `docker push username/repository:tag`;
  - mandatory for pushing to a remote registry: without tagging the image with the registry-specific format (e.g., `username/repository:tag`), Docker won't know which remote repository to push the image to;
  - the common way of tagging, e.g., `repository:tag`, applies only locally, so you'll need to re-tag the image to specify the destination before using `docker push`.

      ```commandline
      docker tag repository:tag username/repository:tag
      ```  
