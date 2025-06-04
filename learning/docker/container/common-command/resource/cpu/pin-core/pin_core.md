# Pin to Specific Cores (CPU Affinity)

**Command:**

```commandline
docker run --cpuset-cpus="0,2" nginx
```

**Command explanation:**

* the `--cpuset-cpus="0,2"` option sets CPU affinity for the container;
* it restricts the container to run only on CPU cores 0 and 2;
* this helps in controlling CPU usage and isolating workloads;


* it can improve performance consistency by avoiding core migrations;
* because the container's processes stay bound to the same CPU cores, improving cache locality and reducing cache-related overhead;
* the container will not use any other CPU cores beyond those specified.
