# What Happens to Container Folder Content?

**Consider this scenario:**

- you want to create an anonymous volume and mount it to the `/data` container folder;
- command:

    ```commandline
    docker run -v /data my_image
    ```

- the `/data` folder already has some content;


- **what will happen to that content after the volume mount?**

**Answer:**

Since the anonymous volume is initially empty because it's a fresh new volume, it will happen like the empty volume case (see [here](../../named/container-content/container_content.md)).
