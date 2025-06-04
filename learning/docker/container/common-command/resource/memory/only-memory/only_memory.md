# Limit Memory

**Command:**

```commandline
docker run -m 512m nginx
```

**Command explanation:**

* `docker run -m 512m nginx` limits the container's memory usage to 512 megabytes;
* the container will be killed if it tries to use more than 512 MB of RAM;
* since the [--memory-swap](../swap/swap.md) flag is not used, Docker limits the container to 512 MB of total memory, meaning it can use **only RAM** and **no additional swap space is available** (swap is slower disk-based memory used when RAM is full);


* if the host system allows, the container might still access some swap by default depending on Docker and kernel settings.
