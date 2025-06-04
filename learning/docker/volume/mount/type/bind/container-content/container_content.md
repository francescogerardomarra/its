# What Happens to Container Folder Content?

**Consider this scenario:**

- you want to create a bind mount within the `/data` container folder;
- the `/data` folder already has some content;
- **what will happen to that content after the bind mount?**

**Answer:**

- the content of `/data` container folder will be **temporary hidden**;
- just the content of the local machine folder is shown (even if it's empty);
- when the container is stopped, the mount is removed so you can see the original content of `/data` folder (see [here](../../../lifetime/lifetime.md)).

**Example:**

- consider that `/data` folder has some files within `my-image`;
- command:

    ```commandline
    docker run -v /home/andrea/test:/data my-image
    ```
- content of `/data` is hidden for both local machine and container until the bind mount is present.
