# Roll Back a Service

**Command:**

```commandline
docker service update --rollback my_service
```

**Command explanation:**

* rolls back the specified service (`my_service`) to its previous version in case of a failed or undesired update;
* reverts any changes made during the last `docker service update` command;
* restores the service's previous task definition, image, configurations, and parameters;


* helps maintain service stability by undoing faulty deployments;
* used when the most recent update causes issues or does not behave as expected.
