# Where They Are Stored

**Bind mounts:**

- data is stored exactly where you specify on the host;
- for example if you specify this: 

    ```commandline
    docker run -v /home/user/data:/app/data my-image
    ```
    - data are stored within `/home/user/data` folder in your local machine.

**Named volumes:**

- stored under `/var/lib/docker/volumes/<volume-name>/_data/` folder on your machine.

**Anonymous volumes:**

- stored under `/var/lib/docker/volumes/<volume-random-id>/_data/` folder on your machine.
