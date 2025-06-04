# Health Checks and Failures

If containers fail to start or fail to pass health checks, the update can:
- retry (depending on your configuration);
- pause;
- or automatically roll back (if `--update-failure-action rollback` was set, and the number of failed new containers exceeds the allowed failure ratio).
