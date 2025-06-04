# Remove All Stopped Containers

**Command:**

```commandline
docker container prune
```

**Command explanation:**

* removes all stopped containers from the Docker system to free up space;
* does not affect running containers, only those that have exited or are in a dead state;
* may prompt for confirmation unless `--force` is used to skip the prompt;


* can help keep your Docker environment clean and avoid clutter from old containers;
* should be used with caution, as removed containers cannot be recovered.
