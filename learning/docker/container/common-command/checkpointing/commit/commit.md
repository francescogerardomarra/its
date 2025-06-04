# Commit Changes to an Image

**Command:**

```commandline
docker commit <container_id_or_name> <new_image_name>
```

**Command explanation:**

* captures the current state of a running or stopped container and saves it as a new image;
* allows creating a reusable image after installing software or making changes inside a container;
* uses `<container_id_or_name>` to identify which container's state to save;


* assigns the specified `<new_image_name>` to the created image, making it easier to reuse or share;
* useful for preserving work done interactively or changes not defined in a `Dockerfile`.
