# Example


1. this is the `Dockerfile`:

   ```dockerfile
   FROM nginx
   
   WORKDIR /app
   
   COPY test.txt .
   
   CMD ["nano", "test.txt"]
   ```

2. enter the `Dockerfile` folder and build the image with `myapp:1.0` tag:

   ```commandline
   docker build -t myapp:1.0 .
   ```

3. see the images with `docker images`:

   ```commandline
   REPOSITORY   TAG       IMAGE ID       CREATED          SIZE
   myapp        1.0       39039c55b78c   13 seconds ago   192MB
   registry     2         4bb5ea59f8e0   18 months ago    24MB
   ```

4. if we relaunch the command `docker build -t myapp:1.0 .`: it will not recreate a new image since the dockerfile hasn't changed;
5. if we launch the command `docker build -t myapp:2.0 .`:
   1. it will not create a new image, it will just assign another tag `myapp:2.0` to that image;
   2. since the `Dockerfile` hasn't changed from previous image;
   3. in that case the image would have two tags (`myapp:1.0`, `myapp:2.0`);
6. we add the tag `myapp:latest` to `myapp:1.0`:

   ```commandline
   docker tag myapp:1.0 myapp:latest
   ```

7. see the images with `docker images`:

   ```commandline
   docker images
   REPOSITORY   TAG       IMAGE ID       CREATED         SIZE
   myapp        1.0       2821aa44bc14   4 minutes ago   192MB
   myapp        latest    2821aa44bc14   4 minutes ago   192MB
   registry     2         4bb5ea59f8e0   18 months ago   24MB
   ```

8. we edit the `Dockerfile` and build a new different image with tag `myapp:2.0`:

   ```dockerfile
   FROM nginx
    
   WORKDIR /app
    
   COPY test.txt .
    
   EXPOSE 8080 // instruction added
    
   CMD ["nano" "test.txt"]
   ```

9. enter the `Dockerfile` folder and build the image with `myapp:2.0` tag:

   ```commandline
   docker build -t myapp:2.0 .
   ```

10. see the images with `docker images`:

    ```commandline
    REPOSITORY   TAG       IMAGE ID       CREATED          SIZE
    myapp        1.0       2821aa44bc14   13 minutes ago   192MB
    myapp        latest    2821aa44bc14   13 minutes ago   192MB
    myapp        2.0       67c5ce172ad4   13 minutes ago   192MB
    registry     2         4bb5ea59f8e0   18 months ago    24MB
    ```

11. we move the tag `myapp:latest` from `myapp:1.0` to `myapp:2.0`:

    ```commandline
    docker tag myapp:2.0 myapp:latest
    ```

12. see the images with `docker images`:

    ```commandline
    REPOSITORY   TAG       IMAGE ID       CREATED         SIZE
    myapp        1.0       2821aa44bc14   2 days ago      192MB
    myapp        2.0       67c5ce172ad4   2 days ago      192MB
    myapp        latest    67c5ce172ad4   2 days ago      192MB
    registry     2         4bb5ea59f8e0   18 months ago   24MB
    ```

13. as you saw, no need to remove the previous tag `myapp:latest` from `myapp:1.0` before creating tag `myapp:latest` to `myapp:2.0`
14. when you run the docker tag command to reassign `myapp:latest` to `myapp:2.0`, Docker will automatically move the `latest` tag to point to the new image (the one associated with `myapp:2.0` in this case);
15. `latest` is a normal tag like others, so this is valid also for other tags;
16. the docker tag command simply assigns or reassigns a tag to an image;
17. it does not require any prior cleanup of the existing tag, Docker will:
    1. reassign `myapp:latest` to point to the image associated with `myapp:2.0`;
    2. leave the image previously associated with `myapp:latest` intact (but it will no longer have the `latest` tag unless it has another tag pointing to it);
18. if `myapp:latest` was the only tag associated with an image, and you reassign `myapp:latest` to a different image, the original image will become [dangling]; // TODO: add link 
19. a dangling image is an image that has no repository or tag associated with it but still exists in your local Docker environment.
