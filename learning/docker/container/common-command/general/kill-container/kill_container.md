# Kill a Running Container

**Command:**

```commandline
docker kill my_container
```

**Command explanation:**

* sends a `SIGKILL` signal to immediately stop the container named `my_container`;
* does not allow the container to gracefully shut down or run any cleanup processes;
* useful in scenarios where the container is unresponsive or needs to be forcefully terminated;


* removes the container from the running state but does not delete it from the system.
