# Benefits

- **quick recovery from failures:**
  - if an update introduces bugs or instability, `--rollback` allows you to return to the last working configuration almost immediately;
- **minimizes downtime:**
  - rather than debugging a broken service live, rolling back can restore service continuity while you troubleshoot separately;
- **safe experimentation:**
  - encourages confident updates, knowing you can revert with a single command if things go wrong;


- **preserves service history:**
  - Docker Swarm maintains the previous configuration automatically, allowing you to roll back without manually reapplying old settings;
- **no manual intervention needed:**
  - you don’t need to remember the previous image, environment variables, or configs, Docker handles the reversion;
- **maintains rolling update behavior:**
  - rollback respects the update configuration settings (such as [parallelism and delay](../../rolling-update/key-component/key_component.md)), ensuring a controlled and safe transition back;
  - when performing a rollback, Docker uses the same update strategy, but in reverse, to revert to the previous version;
  - for example, based on the configuration, Docker will roll back **x** tasks at a time, waiting **y** seconds between each batch.
