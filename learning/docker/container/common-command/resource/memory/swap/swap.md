# Limit Memory + Swap

**Command:**

```commandline
docker run -m 512m --memory-swap 1g nginx
```

**Command explanation:**

* `docker run -m 512m --memory-swap 1g nginx` limits the container to 512 MB of RAM and 1 GB total memory (RAM + swap);
* the container can use up to 512 MB of additional swap (disk-based memory) on top of the 512 MB RAM limit;
* because `--memory-swap 1g` sets the **total memory** (RAM + swap) to 1 GB;
 

* this setup helps prevent the container from being killed immediately when it exceeds RAM usage, by allowing it to swap;
* using swap can degrade performance, but gives the container more virtual memory overall.
