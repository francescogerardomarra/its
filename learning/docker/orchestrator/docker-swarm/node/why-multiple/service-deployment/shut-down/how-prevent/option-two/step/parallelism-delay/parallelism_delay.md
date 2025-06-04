# `--update-parallelism` and `--update-delay`

- this two options controls how many containers are updated at once (`--update-parallelism`) and how long to wait between updates (`--update-delay`);
- this helps reduce the blast radius of updates.


**Command:**

```commandline
docker service update \
--update-parallelism 1 \
--update-delay 10s \
your_service_name
```
