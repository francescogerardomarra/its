# Limit CPU Cores

**Command:**

```commandline
docker run --cpus=1.5 nginx
```

**Command explanation:**

* limits the container to use a maximum of 1.5 CPU cores;
* means the container can fully use one core and 50% of another;
* helps prevent the container from consuming excessive CPU resources;


* useful for performance control and fair resource distribution;
* applies only to systems using CFS (completely fair scheduler);
* CFS is the default process scheduler in most modern Linux distributions;


* if a system uses a different scheduler, the `--cpus` option might not work as expected.
