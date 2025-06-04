# Set CPU Shares (Relative Weight)

**Command:**

```commandline
docker run --cpu-shares=512 nginx
```

**Command explanation:**

* sets the CPU share weight for the container relative to other containers;
* `--cpu-shares` controls how much CPU time a container gets **relative to others** when the CPU is under load;
* `--cpu-shares=512` assigns a weight of 512, which is the default for Docker containers;


* if another container is set with a higher value (e.g., 1024), it will get more CPU time under contention;
* this setting only has an effect when CPU resources are limited or heavily used;
* it does not limit the container's CPU usage when the system is idle.

**Example:**

* suppose you run two containers: one with `--cpu-shares=1024` and another with `--cpu-shares=512`;
* both containers are trying to use the CPU at 100% at the same time (CPU contention happens);
* Docker will allocate CPU time in a 2:1 ratio between the two containers;
 

* this means the first container will get roughly 66% of the CPU, and the second will get about 33%;
* if only one container is running or using CPU, it can still use 100% regardless of its share value.
